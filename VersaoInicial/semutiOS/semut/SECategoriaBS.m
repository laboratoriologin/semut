//
//  SECategoriaBS.m
//  semut
//
//  Created by Julio Rocha on 25/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SECategoriaBS.h"
#import "SECategoria.h"
#import "SECategoriaDAO.h"
#import "SEUtil.h"
@implementation SECategoriaBS {
    NSMutableData *mutableData;
}


-(void) downloadCategoria {
    
    mutableData = [[NSMutableData alloc]init];
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:[kUrlTipoOcorrencias stringByAppendingFormat:@"?key_servlet=%@",[SEUtil servletKey]]] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
    
    (void) [[NSURLConnection alloc] initWithRequest:request delegate:self];
    
    
    
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data {
    
    [mutableData appendData:data];
    
}


- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    
    NSDictionary *resultados = [NSJSONSerialization JSONObjectWithData:mutableData
                                                               options:NSJSONReadingMutableContainers error:nil];
    
    SECategoria *categoria = nil;
    
    SECategoriaDAO *categoriaDAO = [[SECategoriaDAO alloc]init];
    
    [categoriaDAO clear];
    
    for(NSDictionary *item in [resultados objectForKey:@"tipoOcorrencias"])
    {
        
        categoria = [[SECategoria alloc]init];
        
        categoria.codigo = [[item valueForKey:@"id"]longValue];
        categoria.descricao = [item valueForKey:@"nome"];
        categoria.grupo = [[item valueForKey:@"grupoOcorrencia"]longValue];
        
        [categoriaDAO inserir:categoria];
        
    }
    
}

-(NSMutableArray *) findCategoriasTransalvador {
    
    return [[[SECategoriaDAO alloc]init]getAllByGrupo:kGrupoTransalvador];
    
}

-(NSMutableArray *) findCategoriasSucom {
    
    return [[[SECategoriaDAO alloc]init]getAllByGrupo:kGrupoSucom];
}

@end
