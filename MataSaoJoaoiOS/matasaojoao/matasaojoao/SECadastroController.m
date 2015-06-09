//
//  SECadastroController.m
//  semut
//
//  Created by Julio Rocha on 28/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SECadastroController.h"
#import "SEUsuario.h"
#import "MBProgressHUD.h"
#import <AFNetworking/AFNetworking.h>
@interface SECadastroController () {
    MBProgressHUD *progress;
}

@end

@implementation SECadastroController

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
    
    SEUsuario * usuario = [SEUtil getUser];
    
    if(usuario==nil) {
        
        _btLogout.alpha=0;
        
    } else {
        
        _tfNome.text = usuario.nome;
        
        _tfEmail.text = usuario.email;
        
        _tfEmail.enabled=NO;
        
        _tfTelefone.text = usuario.telefone;
        
        _tfSenha.text = usuario.senha;
        
        _tfConfirmaSenha.text = usuario.senha;
        
    }
    
    _tfNome.delegate=self;
    
    _tfEmail.delegate=self;
    
    _tfTelefone.delegate=self;
    
    _tfSenha.delegate=self;
    
    _tfConfirmaSenha.delegate=self;
    
}

#pragma mark UITextFiedDelegate

-(void) touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    
    for(UITextField *field in _txtFields) {
        
        if([field isFirstResponder] ) {
            
            [field resignFirstResponder];
            
            break;
            
        }
        
    }
    
}


-(BOOL) textFieldShouldReturn:(UITextField *)textField{
    
    [textField resignFirstResponder];
    
    return YES;
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    
}

- (IBAction)salvar:(id)sender {
    
    if([self validateFields]) {
        
        NSDictionary *parameters = nil;
        
        if([SEUtil getUser]!=nil) {
     
            parameters= @{@"email": _tfEmail.text,@"nome": _tfNome.text,@"telefone": _tfTelefone.text,@"senha": [_tfSenha.text MD5],@"logado":@"1"};
     
        } else {
       
            parameters= @{@"email": _tfEmail.text,@"nome": _tfNome.text,@"telefone": _tfTelefone.text,@"senha": [_tfSenha.text MD5]};
       
        }
        
        AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
        
        [self showLoading];
        
        [manager POST:[kUrlCadastroUsuario stringByAppendingFormat:@"?key_servlet=%@",[SEUtil servletKey]] parameters:parameters success:^(AFHTTPRequestOperation *operation, id responseObject) {
            
            NSDictionary *resultados = responseObject;
            
            NSString *resultado =[resultados objectForKey:@"status"];
            
            [progress hide:YES];
            
            if([resultado isEqualToString:@"erro"]) {
                
                [[[UIAlertView alloc]initWithTitle:@"OPS!" message:@"Ocorreu um erro nos servidores, tente novamente mais tarde." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
                
            } else if([resultado isEqualToString:@"existe"]) {
                
                [[[UIAlertView alloc]initWithTitle:@"E-mail já existe" message:@"" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
                
            } else {
                
                int codigo = (int) [resultado integerValue];
                
                _tfEmail.enabled=NO;
                
                NSDictionary *dicUser = @{@"email": _tfEmail.text,@"nome": _tfNome.text,@"telefone": _tfTelefone.text,@"senha": _tfSenha.text, @"codigo": [NSNumber numberWithInt:codigo]};
                
                if([SEUtil getUser]==nil) {
                    
                    if(_cadastoConfirmacaoOcorrencia) {
                        
                        [SEUtil saveUser:dicUser];
                        
                        [self.navigationController popViewControllerAnimated:YES];
                        
                    } else {
                        
                        [[[UIAlertView alloc]initWithTitle:@"Cadastrado com sucesso!" message:[NSString stringWithFormat:@"%@, Bem vindo!",_tfNome.text] delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
                        
                    }
                    
                } else {
                    
                    [[[UIAlertView alloc]initWithTitle:@"Dados alterados com sucesso!" message:nil delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
                    
                }
                
                [SEUtil saveUser:dicUser];
                
                _btLogout.alpha=1;
                
                
            }
            
        } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
            [progress hide:YES];
            [[[UIAlertView alloc]initWithTitle:@"OPS!" message:@"Ocorreu um erro nos servidores, tente novamente mais tarde." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
        }];
        
    }
    
    
}

- (IBAction)logout:(id)sender {
    [SEUtil clearUser];
    _btLogout.alpha=0;
    _tfEmail.enabled=YES;
    for(UITextField *field in _txtFields) {
        field.text=@"";
    }
    
}

-(BOOL) validateFields {
    
    for(UITextField *field in _txtFields) {
        if(field.text.length==0) {
            [[[UIAlertView alloc]initWithTitle:@"Preencha todos os campos!" message:@"" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
            return false;
        }
    }
    
    if(![_tfSenha.text isEqualToString:_tfConfirmaSenha.text]) {
        [[[UIAlertView alloc]initWithTitle:@"Senhas não conferem!" message:@"" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
        return false;
    }
   
    if(![SEUtil isEmailValid:_tfEmail.text]) {
        [[[UIAlertView alloc]initWithTitle:@"E-mail inválido!" message:@"" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
        return false;
    }
    
    return true;
    
}

-(void) showLoading {
    progress = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    
    progress.mode = MBProgressHUDModeIndeterminate;
    
    progress.labelText=@"Enviando dados...";
    
    [progress show:YES];
}

@end
