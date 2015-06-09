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
 
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"insert into OCORRENCIA(ID, categoria_id,categoria_descricao,texto,imagem,latitude,longitude,grupo_id,data,pendente,image_path,id_temp,enviando) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)",
                   [NSNumber numberWithLong:obj.codigo],
                   [NSNumber numberWithLong:obj.categoria.codigo],
                   obj.categoria.descricao,
                   obj.texto,
                   obj.imagem,
                   [NSNumber numberWithDouble:obj.latitude],
                   [NSNumber numberWithDouble:obj.longitude],
                   [NSNumber numberWithInt:grupo],
                   obj.data,
                   [NSNumber numberWithInteger:obj.pendente],
                   obj.imagePath,
                   [NSNumber numberWithDouble:obj.codigoTemporario],
                   nil];
        
    }
    @catch (NSException *exception) {
        return NO;
    }
    @finally {
        [SEDBUtil closeConnection:conexao];
    }
    
    return  retorno;
    
    
}

-(BOOL) alterar:(SEOcorrencia *) obj {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"update ocorrencia set id=?,imagem=?, pendente=0 where id_temp = ?",[NSNumber numberWithLong:obj.codigo],obj.imagem,[NSNumber numberWithDouble:obj.codigoTemporario],nil];
        
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
            ocorrencia.imagePath            = [results stringForColumn:@"image_path"];
            ocorrencia.codigoTemporario     = [results doubleForColumn:@"id_temp"];
            ocorrencia.pendente             = [results intForColumn:@"pendente"];
            ocorrencia.pendente             = [results intForColumn:@"enviando"];            
            
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

-(SEOcorrencia *) getPendente:(double) codigo {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    @try {
        
        FMResultSet *results = [conexao executeQuery:@"select * from ocorrencia where id_temp=?" withArgumentsInArray:@[[NSNumber numberWithDouble:codigo]]];
        
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
            ocorrencia.imagePath            = [results stringForColumn:@"image_path"];
            ocorrencia.codigoTemporario     = [results doubleForColumn:@"id_temp"];
            ocorrencia.enviando             = [results intForColumn:@"enviando"];
            ocorrencia.pendente             = [results intForColumn:@"pendente"];
            
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
            ocorrencia.imagePath            = [results stringForColumn:@"image_path"];
            ocorrencia.codigoTemporario     = [results doubleForColumn:@"id_temp"];
            ocorrencia.pendente             = [results intForColumn:@"pendente"];
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

-(NSMutableArray *) getPendentes {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    @try {
        
        NSMutableArray *array = [[NSMutableArray alloc]init];
        
        FMResultSet *results = [conexao executeQuery:@"select * from ocorrencia where pendente=1 and enviando = 0"];
        
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
            ocorrencia.imagePath            = [results stringForColumn:@"image_path"];            
            ocorrencia.codigoTemporario     = [results doubleForColumn:@"id_temp"];
            ocorrencia.pendente             = [results intForColumn:@"pendente"];            
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

-(BOOL) setEnviando:(SEOcorrencia *) obj enviando:(int) enviando {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"update OCORRENCIA set enviando = ? where id_temp=?",[NSNumber numberWithInt:enviando], [NSNumber numberWithDouble:obj.codigoTemporario],nil];
        
    }
    @catch (NSException *exception) {
        return NO;
    }
    @finally {
        [SEDBUtil closeConnection:conexao];
    }
    
    return  retorno;
    
}

-(BOOL) setAllEnviando:(BOOL) enviando {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"update OCORRENCIA set enviando = ?",[NSNumber numberWithBool:enviando],nil];
        
    }
    @catch (NSException *exception) {
        return NO;
    }
    @finally {
        [SEDBUtil closeConnection:conexao];
    }
    
    return  retorno;
    
}

@end
