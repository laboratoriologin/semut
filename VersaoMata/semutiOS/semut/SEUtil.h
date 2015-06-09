//
//  SEUtil.h
//  semut
//
//  Created by Julio Rocha on 25/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSString(MD5)

- (NSString *)MD5;

@end

@interface UIColor(HexaString)

#define kCorSucom @"#999D13"
#define kCorTransalvador @"#d13900"

+(UIColor *) colorFromHexString:(NSString *) cor;
+(UIColor *) sucomColor;
+(UIColor *) transalvadorColor;

@end

#import "SEUsuario.h"

@interface SEUtil : NSObject

#define kBoolSucom @"sucom"

+(SEUsuario *) getUser;

+(void) saveUser:(NSDictionary *) user;

+ (void) setDefault:(id)dic forKey:(NSString *) key;
+ (void) setBool:(BOOL)param forKey:(NSString *) key;
+ (BOOL) boolForKey:(NSString *) key;
+ (id) defaultForKey:(NSString *) key;
+(void) clearUser;
+(NSString *) servletKey;
+(BOOL) isEmailValid:(NSString *) email;

@end

@interface SEConstantes : NSObject


#define kUrlImagem @"http://177.1.212.50:9004/arquivos_semut/"
#define kUrlCadastroUsuario @"http://177.1.212.50:9004/SemutADM/servlet/set_usuario"
#define kUrlAutenticarUsuario @"http://177.1.212.50:9004/SemutADM/servlet/UsuarioServlet"
#define kUrlCadastroToken @"http://177.1.212.50:9004/SemutADM/servlet/novo_token?modelo=iphone"
#define kUrlAlerta @"http://177.1.212.50:9004/SemutADM/servlet/OcorrenciaServlet?status=1"
#define kUrlInsertOcorrencia @"http://177.1.212.50:9004/SemutADM/servlet/insert_ocorrencia"
#define kUrlNoticiaTransalvador @"http://177.1.212.50:9004/SemutADM/servlet/NoticiaServlet?tipoNoticia=2"
#define kUrlNoticiaTransalvadorEducacao @"http://177.1.212.50:9004/SemutADM/servlet/NoticiaServlet?tipoNoticia=5"
#define kUrlNoticiaSemut @"http://177.1.212.50:9004/SemutADM/servlet/NoticiaServlet?tipoNoticia=4"
#define kUrlTipoOcorrencias @"http://177.1.212.50:9004/SemutADM/servlet/TipoOcorrenciaServlet"
#define kTelefoneTransalvador @"tel:21093600"
#define kTelefoneSucom  @"tel:22016900"
#define kTelefonePoluicaoSonora  @"tel:22016660"
#define kChaveServlet @"5Mu1tL0g1N"

#define kGrupoTransalvador 1
#define kGrupoSucom 2

#define kBoolPushReceived @"push_received"
#define kBoolEducacaoTransito @"educacao_transito"

#define kToken @"token"


@end