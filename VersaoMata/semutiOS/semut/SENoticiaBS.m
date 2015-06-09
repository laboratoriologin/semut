//
//  SENoticiaBS.m
//  semut
//
//  Created by Julio Rocha on 27/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SENoticiaBS.h"
#import "SENoticia.h"
#import "SEUtil.h"

@interface SENoticiaBS() {
    NSDateFormatter *formatterMili;
    NSDateFormatter *formatterHourMinute;
}

@end

@implementation SENoticiaBS

-(void) findNoticiasTransalvador:(NSMutableArray *) _resultado {
    
    formatterMili = [[NSDateFormatter alloc]init];
    
    [formatterMili setDateFormat:@"yyyy-MM-dd HH:mm:ss.SSS"];
    
    formatterHourMinute = [[NSDateFormatter alloc]init];
    
    [formatterHourMinute setDateFormat:@"dd/MM/yyyy HH:mm"];
    
    mutableData = [[NSMutableData alloc]init];
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:[kUrlNoticiaTransalvador stringByAppendingFormat:@"&key_servlet=%@",[SEUtil servletKey]]] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
    
    resultado = _resultado;
    
    (void) [[NSURLConnection alloc] initWithRequest:request delegate:self];

    
}

-(void) findNoticiasTransalvadorEducacao:(NSMutableArray *) _resultado {
    
    formatterMili = [[NSDateFormatter alloc]init];
    
    [formatterMili setDateFormat:@"yyyy-MM-dd HH:mm:ss.SSS"];
    
    formatterHourMinute = [[NSDateFormatter alloc]init];
    
    [formatterHourMinute setDateFormat:@"dd/MM/yyyy HH:mm"];
    
    mutableData = [[NSMutableData alloc]init];
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:[kUrlNoticiaTransalvadorEducacao stringByAppendingFormat:@"&key_servlet=%@",[SEUtil servletKey]]] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
    
    resultado = _resultado;
    
    (void) [[NSURLConnection alloc] initWithRequest:request delegate:self];
    
    
}

-(void) findNoticiasSucom:(NSMutableArray *) _resultado {
    
    formatterMili = [[NSDateFormatter alloc]init];
    
    [formatterMili setDateFormat:@"yyyy-MM-dd HH:mm:ss.SSS"];
    
    formatterHourMinute = [[NSDateFormatter alloc]init];
    
    [formatterHourMinute setDateFormat:@"dd/MM/yyyy HH:mm"];
    
    mutableData = [[NSMutableData alloc]init];
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:[kUrlNoticiaSemut stringByAppendingFormat:@"&key_servlet=%@",[SEUtil servletKey]]] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
    
    resultado = _resultado;
    
    (void) [[NSURLConnection alloc] initWithRequest:request delegate:self];
    
    
    
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data {
    
    [mutableData appendData:data];
    
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
    if(self.completionHandler) {
        self.completionHandler();
    }
    
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    
    NSDictionary *resultados = [NSJSONSerialization JSONObjectWithData:mutableData
                                                               options:NSJSONReadingMutableContainers error:nil];
    
    SENoticia *noticia = nil;
    
    for(NSDictionary *item in [resultados objectForKey:@"noticias"])
    {
        
        noticia = [[SENoticia alloc]init];
        
        noticia.codigo = [[item valueForKey:@"id"]longValue];
        noticia.titulo = [item valueForKey:@"titulo"];
        noticia.data = [item valueForKey:@"data"];
        noticia.data = [formatterHourMinute stringFromDate:[formatterMili dateFromString:noticia.data]];
        noticia.descricao = [item valueForKey:@"descricao"];
        noticia.imagem = [item valueForKey:@"imagem"];
        
        [resultado addObject:noticia];
        
    }
    
    
    if(self.completionHandler) {
        self.completionHandler();
    }
    
}

@end
