//
//  SEAlertaBS.m
//  semut
//
//  Created by Julio Rocha on 10/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEAlertaBS.h"
#import "SEUtil.h"
#import "SEOcorrencia.h"
@interface SEAlertaBS() {
    NSDateFormatter *formatterMili;
    NSDateFormatter *formatterHourMinute;
}
@end

@implementation SEAlertaBS

-(void) findAlertas:(NSMutableArray *) _resultado {
    
    formatterMili = [[NSDateFormatter alloc]init];
    
    [formatterMili setDateFormat:@"yyyy-MM-dd HH:mm:ss.SSS"];

    formatterHourMinute = [[NSDateFormatter alloc]init];
    
    [formatterHourMinute setDateFormat:@"dd/MM/yyyy HH:mm"];
    
    mutableData = [[NSMutableData alloc]init];
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:[kUrlAlerta stringByAppendingFormat:@"&key_servlet=%@",[SEUtil servletKey]]] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
    
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
    
    SEOcorrencia *ocorrencia = nil;
    
    for(NSDictionary *item in [resultados objectForKey:@"Ocorrencias"])
    
    {
        
        ocorrencia = [[SEOcorrencia alloc]init];
        
        ocorrencia.texto = [item valueForKey:@"descricao"];
        
        ocorrencia.categoria = [[SECategoria alloc]init];
        
        ocorrencia.categoria.descricao = [item valueForKey:@"nomeTipoOcorrencia"];
        
        ocorrencia.imagem = [item valueForKey:@"imagem"];
        
        ocorrencia.data = [item valueForKey:@"dataAlerta"];
        
        ocorrencia.data = [formatterHourMinute stringFromDate:[formatterMili dateFromString:ocorrencia.data]];
        
        [resultado addObject:ocorrencia];
        
    }
    
    
    if(self.completionHandler) {
        self.completionHandler();
    }
    
}

@end
