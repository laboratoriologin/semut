//
//  SENoticiaDAO.m
//  semut
//
//  Created by Julio Rocha on 19/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SENoticiaDAO.h"

@implementation SENoticiaDAO

-(BOOL) inserir:(SENoticia *) obj grupo:(int) grupo {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"insert into NOTICIA(ID, titulo,descricao,imagem,grupo_id,data, educacao_transito) values(?, ?, ?, ?, ?, ? , ?)",
                   [NSNumber numberWithLong:obj.codigo],
                   obj.titulo,
                   obj.descricao,
                   obj.imagem,
                   [NSNumber numberWithInt:grupo],obj.data,
                   [NSNumber numberWithBool:obj.educacaoTransito],
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

-(BOOL) excluir:(SENoticia *) obj {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"delete from noticia where id = ?", [NSNumber numberWithLong:obj.codigo],nil];
        
    }
    @catch (NSException *exception) {
        return NO;
    }
    @finally {
        [SEDBUtil closeConnection:conexao];
    }
    
    return  retorno;
    
}

-(SENoticia *) get:(int)codigo {
    
    FMDatabase *conexao = [SEDBUtil getConnection];
    
    @try {
        
        FMResultSet *results = [conexao executeQuery:@"select * from noticia where id=?" withArgumentsInArray:@[[NSNumber numberWithInt:codigo]]];
        
        SENoticia *noticia = nil;
        
        if([results next]) {
            noticia                  = [[SENoticia alloc]init];
            noticia.codigo           = [results longForColumn:@"id"];
            noticia.titulo           = [results stringForColumn:@"titulo"];
            noticia.data             = [results stringForColumn:@"data"];
            noticia.descricao        = [results stringForColumn:@"descricao"];
            noticia.imagem           = [results stringForColumn:@"imagem"];
            noticia.educacaoTransito = [results boolForColumn:@"educacao_transito"];
        }
        
        return noticia;
        
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
        
        FMResultSet *results = [conexao executeQuery:@"select * from noticia where grupo_id=?" withArgumentsInArray:@[[NSNumber numberWithInt:grupo]]];
        
        SENoticia *noticia = nil;
        
        while([results next]) {
            noticia                  = [[SENoticia alloc]init];
            noticia.codigo           = [results longForColumn:@"id"];
            noticia.titulo           = [results stringForColumn:@"titulo"];
            noticia.data             = [results stringForColumn:@"data"];
            noticia.descricao        = [results stringForColumn:@"descricao"];
            noticia.imagem           = [results stringForColumn:@"imagem"];
            noticia.educacaoTransito = [results boolForColumn:@"educacao_transito"];
            [array addObject:noticia];
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
