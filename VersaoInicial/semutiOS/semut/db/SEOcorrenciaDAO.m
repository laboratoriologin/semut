//
//  SEOcorrenciaDAO.m
//  semut
//
//  Created by Julio Rocha on 06/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEOcorrenciaDAO.h"
#import "FMDatabase.h"
#import "SEDBUtil.h"
#import "SEOcorrencia.h"
@implementation SEOcorrenciaDAO

-(BOOL) inserir:(SEOcorrencia *) obj grupo:(int) grupo {

    NSDateFormatter *formatter = [[NSDateFormatter alloc]init];
    
    [formatter setDateFormat:@"dd/MM/yyyy HH:mm"];;
    
    [obj setData:[formatter stringFromDate:[[NSDate alloc]init]]];
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"insert into OCORRENCIA(ID, categoria_id,categoria_descricao,texto,imagem,latitude,longitude,grupo_id,data) values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                   [NSNumber numberWithLong:obj.codigo],
                   [NSNumber numberWithLong:obj.categoria.codigo],
                   obj.categoria.descricao,
                   obj.texto,
                   obj.imagem,
                   [NSNumber numberWithDouble:obj.latitude],
                   [NSNumber numberWithDouble:obj.longitude],
                   [NSNumber numberWithInt:grupo],obj.data,nil];
        
    }
    @catch (NSException *exception) {
        return NO;
    }
    @finally {
        [SEDBUtil closeConnection:conexao];
    }
    
    return  retorno;
    
    
}

-(BOOL) excluir:(SEOcorrencia *) obj {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"delete from OCORRENCIA where id = ?", [NSNumber numberWithLong:obj.codigo],nil];
        
    }
    @catch (NSException *exception) {
        return NO;
    }
    @finally {
        [SEDBUtil closeConnection:conexao];
    }
    
    return  retorno;
    
}

-(SEOcorrencia *) get:(long) codigo {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    @try {
        
        FMResultSet *results = [conexao executeQuery:@"select * from ocorrencia where id=?" withArgumentsInArray:@[[NSNumber numberWithInt:codigo]]];
        
        SEOcorrencia *ocorrencia = nil;
        
        if([results next]) {
            ocorrencia                      = [[SEOcorrencia alloc]init];
            ocorrencia.codigo               = [results longForColumn:@"id"];
            ocorrencia.texto                = [results stringForColumn:@"texto"];
            ocorrencia.data                 = [results stringForColumn:@"data"];
            ocorrencia.categoria            = [[SECategoria alloc]init];
            ocorrencia.categoria.codigo     = [results longForColumn:@"categoria_id"];
            ocorrencia.categoria.descricao  = [results stringForColumn:@"categoria_descricao"];
            ocorrencia.latitude             = [results doubleForColumn:@"latitude"];
            ocorrencia.longitude            = [results doubleForColumn:@"longitude"];
            ocorrencia.imagem               = [results stringForColumn:@"imagem"];
            
        }
        
        return ocorrencia;
        
    }
    @catch (NSException *exception) {
        return nil;
    }
    @finally {
        [SEDBUtil closeConnection:conexao];
    }
    
}


-(NSMutableArray *) getAllByGrupo:(int)grupo {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    @try {
        
        NSMutableArray *array = [[NSMutableArray alloc]init];
        
        FMResultSet *results = [conexao executeQuery:@"select * from ocorrencia where grupo_id=?" withArgumentsInArray:@[[NSNumber numberWithInt:grupo]]];
        
        SEOcorrencia *ocorrencia = nil;
        
        while([results next]) {
            ocorrencia                      = [[SEOcorrencia alloc]init];
            ocorrencia.codigo               = [results longForColumn:@"id"];
            ocorrencia.texto                = [results stringForColumn:@"texto"];
            ocorrencia.data                 = [results stringForColumn:@"data"];
            ocorrencia.categoria            = [[SECategoria alloc]init];
            ocorrencia.categoria.codigo     = [results longForColumn:@"categoria_id"];
            ocorrencia.categoria.descricao  = [results stringForColumn:@"categoria_descricao"];
            ocorrencia.latitude             = [results doubleForColumn:@"latitude"];
            ocorrencia.longitude            = [results doubleForColumn:@"longitude"];
            ocorrencia.imagem               = [results stringForColumn:@"imagem"];
            [array addObject:ocorrencia];   
        }
        
        return array;
        
    }
    @catch (NSException *exception) {
        return nil;
    }
    @finally {
        [SEDBUtil closeConnection:conexao];
    }
}

@end
