//
//  SECategoriaDAO.m
//  semut
//
//  Created by Julio Rocha on 04/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SECategoriaDAO.h"
#import "FMDatabase.h"
#import "SEDBUtil.h"
@implementation SECategoriaDAO

-(BOOL) inserir:(SECategoria *) obj {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"insert into CATEGORIA(ID, DESCRICAO,GRUPO) values(?, ?, ?)",
                   [NSNumber numberWithLong:obj.codigo],
                   obj.descricao,
                   [NSNumber numberWithLong:obj.grupo],nil];
        
    }
    @catch (NSException *exception) {
        return NO;
    }
    @finally {
        [SEDBUtil closeConnection:conexao];
    }
    
    return  retorno;
    
    
}

-(BOOL) clear {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"DELETE FROM CATEGORIA",nil];
        
    }
    @catch (NSException *exception) {
        return NO;
    }
    @finally {
        [SEDBUtil closeConnection:conexao];
    }
    
    return  retorno;
    
    
}

-(NSMutableArray *) getAllByGrupo:(int)grupo {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    @try { 
        
        NSMutableArray *array = [[NSMutableArray alloc]init];
        
        FMResultSet *results = [conexao executeQuery:@"select * from categoria where grupo=?" withArgumentsInArray:@[[NSNumber numberWithInt:grupo]]];
        
        SECategoria *categoria = nil;
        
        while([results next]) {
            categoria = [[SECategoria alloc]init];
            categoria.descricao = [results stringForColumn:@"descricao"];
            categoria.codigo  = [results longForColumn:@"id"];
            categoria.grupo  = [results longForColumn:@"grupo"];
            [array addObject:categoria];
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
