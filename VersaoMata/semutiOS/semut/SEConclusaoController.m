//
//  SEConclusaoController.m
//  semut
//
//  Created by Julio Rocha on 26/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEConclusaoController.h"
#import "SEUtil.h"
#import <QuartzCore/QuartzCore.h>
#import "SECadastroController.h"
#import "MBProgressHUD.h"
#import <AFNetworking/AFNetworking.h>
#import "SEUsuarioBS.h"
#import "SEOcorrenciaDAO.h"
#define kBotaoOk 0
#define kBotaoCadastro 1
#define kBotaoCancelar 2
#define kTagAlertViewLogin 10


@interface SEConclusaoController () {
    MBProgressHUD *progress;
}

@end

@implementation SEConclusaoController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
}

-(void) viewWillAppear:(BOOL)animated {
    
    SEUsuario *usuario = [SEUtil getUser];
    
    NSDate * date = [[NSDate alloc]init];
    
    NSDateFormatter *formatter = [[NSDateFormatter alloc]init];
    
    [formatter setDateFormat:@"dd/MM/yyyy"];
    
    _lbData.text  = [formatter stringFromDate:date];
    
    [formatter setDateFormat:@"HH:mm"];
    
    _lbHora.text  = [formatter stringFromDate:date];
    
    _lbCategoriaOcorrencia.text = _ocorrencia.categoria.descricao;
    
    _lbTexto.text = _ocorrencia.texto;
    
    if(usuario == nil) {
        
        [self showAlertLogin];
        
    } else {
        
        _lbNome.text = usuario.nome;
        
        
    }
    
    if([SEUtil boolForKey:kBoolSucom]) {
        
        for(UILabel *label in _labels) {
            
            [label setTextColor:[UIColor colorFromHexString:kCorSucom]];
            
        }
        
        [_btConfirmar setTitleColor:[UIColor colorFromHexString:kCorSucom] forState:UIControlStateNormal];
        
    }
    
    [_lbTexto sizeToFit];
    
    if(_ocorrencia.imageData) {
    
        _imagem.image = [UIImage imageWithData:_ocorrencia.imageData];
        
        [_imagem setContentMode:UIViewContentModeScaleAspectFit];
        
    }
    
}

-(void) showAlertLogin {
    
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Autentique-se" message:nil delegate:self cancelButtonTitle:@"OK" otherButtonTitles:@"Cadastro",@"Cancelar",nil];
    
    alert.tag = kTagAlertViewLogin;
    
    [alert setAlertViewStyle:UIAlertViewStyleLoginAndPasswordInput];
    
    [alert show];
}

#pragma mark AlertViewDelegate

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    
    if(alertView.tag==kTagAlertViewLogin) {
    
        switch (buttonIndex) {
                
            case kBotaoCadastro:
                [self performSegueWithIdentifier:@"sg_cadastro" sender:nil];
                break;
                
            case kBotaoCancelar:
                [self.navigationController popViewControllerAnimated:YES];
                break;
                
            case kBotaoOk: {
                
                SEUsuario *user =  [[SEUsuario alloc]init];
                
                user.email = [[alertView textFieldAtIndex:0]text];
                
                user.senha = [[alertView textFieldAtIndex:1]text];
                
                [self showLoading];
                
                SEUsuarioBS *usuarioBS = [[SEUsuarioBS alloc] init];
                
                [usuarioBS setCompletionHandler:^{
                    
                    [progress hide:YES];
                    
                    if(user.codigo!=0) {
                        
                        NSDictionary *dicUser = @{@"email": user.email,@"nome": user.nome,@"telefone": user.telefone,@"senha": user.senha, @"codigo": [NSNumber numberWithInt:user.codigo]};
                        
                        [SEUtil saveUser:dicUser];
                        
                        _lbNome.text = user.nome;
                        
                    } else {
                        
                        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Usuário inválido" message:@"Tente novamente" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:@"Cadastro",@"Cancelar",nil];
                        
                        alert.tag = kTagAlertViewLogin;
                        
                        [alert setAlertViewStyle:UIAlertViewStyleLoginAndPasswordInput];
                        
                        [alert show];
                        
                    }
                    
                }];
                
                [usuarioBS authenticate:user];
                
                break;
                
            }
        };

    } else  {
        
        [self.navigationController popToRootViewControllerAnimated:YES];
        
    }
    
}

-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    SECadastroController *controller = segue.destinationViewController;
    controller.cadastoConfirmacaoOcorrencia = YES;
}

- (IBAction)confirmar:(id)sender {
    
    [self showLoading];
    
    NSDictionary *parameters = nil;
    
    if(_ocorrencia.texto.length==0) {
        parameters = @{@"longitude": [NSNumber numberWithDouble:_ocorrencia.longitude],@"latitude": [NSNumber numberWithDouble:_ocorrencia.latitude],@"descricao":@"", @"id": [NSNumber numberWithInt:[[SEUtil getUser]codigo]],@"categoria":[NSNumber numberWithLong:_ocorrencia.categoria.codigo]};
        
    } else {
        parameters = @{@"longitude": [NSNumber numberWithDouble:_ocorrencia.longitude],@"latitude": [NSNumber numberWithDouble:_ocorrencia.latitude],@"descricao":_ocorrencia.texto, @"id": [NSNumber numberWithInt:[[SEUtil getUser]codigo]],@"categoria":[NSNumber numberWithLong:_ocorrencia.categoria.codigo]};
        
    }
    
    if(_ocorrencia.imageData) {
    
        [self postComImagem:parameters];
        
    } else {
        
        [self postSemImagem:parameters];
        
    }
    
    
}

-(void) postComImagem:(NSDictionary *) parameters {
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    
    [manager POST:[kUrlInsertOcorrencia stringByAppendingFormat:@"?key_servlet=%@",[SEUtil servletKey]] parameters:parameters constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
        
        if(_ocorrencia.imageData) {
            
            [formData appendPartWithFormData:_ocorrencia.imageData name:@"file"];
            
        }
        
    } success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        [self checkResultado:responseObject];
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        [progress hide:YES];
        
        [[[UIAlertView alloc]initWithTitle:@"OPS!" message:@"Ocorreu um erro nos servidores, tente novamente mais tarde." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
        
    }];
    
}

-(void) postSemImagem:(NSDictionary *) parameters {
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    
    [manager POST:[kUrlInsertOcorrencia stringByAppendingFormat:@"?key_servlet=%@",[SEUtil servletKey]] parameters:parameters  success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        [self checkResultado:responseObject];
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        [progress hide:YES];
        
        [[[UIAlertView alloc]initWithTitle:@"OPS!" message:@"Ocorreu um erro nos servidores, tente novamente mais tarde." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
        
    }];

    
}

-(void) checkResultado:(NSDictionary *) resultados {
    
    [progress hide:YES];
    
    NSString *resultado =[resultados objectForKey:@"status"];
    
    int protocolo = [resultado intValue];
    
    if(protocolo==0) {
        
        [[[UIAlertView alloc]initWithTitle:@"OPS!" message:@"Ocorreu um erro nos servidores, tente novamente mais tarde." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
        
    } else {
        
        [self persistOcorrencia:protocolo];
        
        [[[UIAlertView alloc]initWithTitle:[[SEUtil getUser]nome] message:[NSString stringWithFormat:@"Seu chamado foi atendido com sucesso.\n O número do protocolo é o %d. Obrigado!",protocolo  ] delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
        
    }
    
}

-(void) persistOcorrencia:(int) protocolo {
    
    _ocorrencia.codigo = protocolo;
    
    if(_ocorrencia.imageData) {
        _ocorrencia.imagem = [NSString stringWithFormat:@"%d.jpg",protocolo];
    }
    
    int grupo = [SEUtil boolForKey:kBoolSucom]?kGrupoSucom:kGrupoTransalvador;
    
    [[[SEOcorrenciaDAO alloc]init]inserir:_ocorrencia grupo:grupo];
    
}

-(void) showLoading {
 
    progress = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    
    progress.mode = MBProgressHUDModeIndeterminate;
    
    progress.labelText=@"Enviando dados...";
    
    [progress show:YES];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

@end
