//
//  SEUsuarioBS.m
//  semut
//
//  Created by Julio Rocha on 12/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEUsuarioBS.h"
#import "SEUtil.h"
@interface SEUsuarioBS() {
    SEUsuario *usuario;
}
@end

@implementation SEUsuarioBS

-(void) authenticate:(SEUsuario *) _usuario {
    
    mutableData = [[NSMutableData alloc]init];
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:[kUrlAutenticarUsuario stringByAppendingFormat:@"?email=%@&key_servlet=%@",_usuario.email,[SEUtil servletKey]]] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
    
    usuario = _usuario;
    
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
    
    NSDictionary *item  = [resultados objectForKey:@"usuario"];
    
    if(item) {
        
        NSString *senha = [item valueForKey:@"senha"];
    
        if([[usuario.senha MD5] isEqualToString:senha]) {
            
            usuario.codigo = [[item valueForKey:@"id"]intValue];
            
            usuario.nome = [item valueForKey:@"nome"];
            
            usuario.telefone = [item valueForKey:@"telefone"];

            
        }
    }

    if(self.completionHandler) {
        self.completionHandler();
    }
    
}

@end
