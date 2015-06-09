//
//  SEOcorrenciaBS.m
//  semut
//
//  Created by Julio Rocha on 09/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "SEOcorrenciaBS.h"
#import "SEOcorrenciaDAO.h"
#import <AFNetworking/AFNetworking.h>
#import "SEUtil.h"
@implementation SEOcorrenciaBS

-(NSDictionary *) parametersFromOcorrencia:(SEOcorrencia *) ocorrencia {
    
    NSDictionary *parameters = nil;
    
    if(ocorrencia.texto.length==0) {
        parameters = @{@"longitude": [NSNumber numberWithDouble:ocorrencia.longitude],@"latitude": [NSNumber numberWithDouble:ocorrencia.latitude],@"descricao":@"", @"id": [NSNumber numberWithInt:[[SEUtil getUser]codigo]],@"categoria":[NSNumber numberWithLong:ocorrencia.categoria.codigo]};
    } else {
        parameters = @{@"longitude": [NSNumber numberWithDouble:ocorrencia.longitude],@"latitude": [NSNumber numberWithDouble:ocorrencia.latitude],@"descricao":[[NSString alloc]initWithData:[ocorrencia.texto dataUsingEncoding:NSUTF8StringEncoding] encoding:NSUTF8StringEncoding], @"id": [NSNumber numberWithInt:[[SEUtil getUser]codigo]],@"categoria":[NSNumber numberWithLong:ocorrencia.categoria.codigo]};
        
    }
    
    return parameters;
    
}

-(void) createOcorrencia {
    
    if(_ocorrencia.imageData) {
        
        [self postComImagem:[self parametersFromOcorrencia:_ocorrencia]];
        
    } else {
        
        [self postSemImagem:[self parametersFromOcorrencia:_ocorrencia]];
        
    }
    
}

-(void) postComImagem:(NSDictionary *) parameters {
    
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentsDirectory = [paths objectAtIndex:0];
    
    NSTimeInterval interval = [[[NSDate alloc]init]timeIntervalSince1970];
    
    _ocorrencia.codigoTemporario = interval;
    
    NSString *imagePath = [documentsDirectory stringByAppendingPathComponent:[NSString stringWithFormat:@"%f.png",interval]];
    
    _ocorrencia.imagePath = imagePath;
    
    [_ocorrencia.imageData writeToFile:imagePath atomically:NO];
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    
    [manager POST:[kUrlInsertOcorrencia stringByAppendingFormat:@"?key_servlet=%@",[SEUtil servletKey]] parameters:parameters constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
        
        if(_ocorrencia.imageData) {
            
            [formData appendPartWithFormData:_ocorrencia.imageData name:@"file"];
            
        }
        
    } success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        [self checkResultado:responseObject];
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        [self persistOcorrencia:0 pendente:YES];
        
    }];
    
}

-(void) postSemImagem:(NSDictionary *) parameters {
    
    NSTimeInterval interval = [[[NSDate alloc]init]timeIntervalSince1970];
    
    _ocorrencia.codigoTemporario = interval;
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    
    [manager POST:[kUrlInsertOcorrencia stringByAppendingFormat:@"?key_servlet=%@",[SEUtil servletKey]] parameters:parameters  success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        [self checkResultado:responseObject];
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        [self persistOcorrencia:0 pendente:YES];
        
    }];
    
    
}

-(void) checkResultado:(NSDictionary *) resultados {
    
    NSString *resultado =[resultados objectForKey:@"status"];
    
    int protocolo = [resultado intValue];
    
    if(protocolo==0) {
        
        [self persistOcorrencia:protocolo pendente:YES];
        
    } else {
        
        [self persistOcorrencia:protocolo pendente:NO];
        
    }
    
}

-(void) persistOcorrencia:(int) protocolo pendente:(BOOL) pendente {
    
    _ocorrencia.codigo = protocolo;
    
    if(_ocorrencia.imageData) {
        _ocorrencia.imagem = [NSString stringWithFormat:@"%d.jpg",protocolo];
    }
    
    if(pendente) {
        _ocorrencia.pendente=1;
        _ocorrencia.codigo = (int) _ocorrencia.codigoTemporario;
    }
    
    int grupo = [SEUtil boolForKey:kBoolSucom]?kGrupoSucom:kGrupoTransalvador;
    
    [[[SEOcorrenciaDAO alloc]init]inserir:_ocorrencia grupo:grupo];
    
}

#pragma mark - Update ocorrencia


-(void) enviarOcorrenciasPendentes {
    
    _ocorrenciaDAO = [[SEOcorrenciaDAO alloc]init];
    
    NSMutableArray *pendentes = [_ocorrenciaDAO getPendentes];
    
    for(SEOcorrencia *ocorrencia in pendentes) {
        
        [self updateOcorrencia:ocorrencia];
        
    }
    
}


-(void) updateOcorrencia:(SEOcorrencia *) ocorrencia {
    
    if([[_ocorrenciaDAO getPendente:ocorrencia.codigoTemporario]enviando]==0) {
    
        [_ocorrenciaDAO setEnviando:ocorrencia enviando:1];
        
        if(ocorrencia.imagePath) {
            
            [self updateOcorrenciaComImagem:ocorrencia];
            
        } else {
            
            [self updateOcorrenciaSemImagem:ocorrencia];
            
        }
        
    }
    
}

-(void) updateOcorrenciaComImagem:(SEOcorrencia *) ocorrencia {
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    
    NSDictionary *parameters =[self parametersFromOcorrencia:ocorrencia];
    
    [manager POST:[kUrlInsertOcorrencia stringByAppendingFormat:@"?key_servlet=%@",[SEUtil servletKey]] parameters:parameters constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
        
        [formData appendPartWithFormData:[NSData dataWithContentsOfFile:ocorrencia.imagePath] name:@"file"];
        
    } success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        [self checkResultadoUpdate:responseObject ocorrencia:ocorrencia];
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        [_ocorrenciaDAO setEnviando:ocorrencia enviando:0];
    }];
    
}

-(void) updateOcorrenciaSemImagem:(SEOcorrencia *) ocorrencia {
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    
    [manager POST:[kUrlInsertOcorrencia stringByAppendingFormat:@"?key_servlet=%@",[SEUtil servletKey]] parameters:[self parametersFromOcorrencia:ocorrencia]  success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        [self checkResultadoUpdate:responseObject ocorrencia:ocorrencia];
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        [_ocorrenciaDAO setEnviando:ocorrencia enviando:0];
    }];
    
}

-(void) checkResultadoUpdate:(NSDictionary *) resultados ocorrencia:(SEOcorrencia *) ocorrencia{
    
    NSString *resultado =[resultados objectForKey:@"status"];
    
    int protocolo = [resultado intValue];
    
    if(protocolo!=0) {
     
        [self alterarOcorrencia:protocolo ocorrencia:ocorrencia];
        
    }
    
    [_ocorrenciaDAO setEnviando:ocorrencia enviando:0];
    
}

-(void) alterarOcorrencia:(int) protocolo ocorrencia:(SEOcorrencia*) ocorrencia {
    
    ocorrencia.codigo = protocolo;
    
    if(ocorrencia.imagePath) {
        ocorrencia.imagem = [NSString stringWithFormat:@"%d.jpg",protocolo];
    }
    
    [_ocorrenciaDAO alterar:ocorrencia];
    
    
}


@end
