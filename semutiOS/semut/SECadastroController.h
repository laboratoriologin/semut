//
//  SECadastroController.h
//  semut
//
//  Created by Julio Rocha on 28/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SEUtil.h"
#import "SEUsuario.h"
@interface SECadastroController : UIViewController <UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UITextField *tfNome;
@property (weak, nonatomic) IBOutlet UITextField *tfEmail;
@property (weak, nonatomic) IBOutlet UITextField *tfTelefone;
@property (weak, nonatomic) IBOutlet UITextField *tfSenha;
@property (weak, nonatomic) IBOutlet UITextField *tfConfirmaSenha;
@property (strong, nonatomic) IBOutletCollection(UITextField) NSArray *txtFields;

@property(nonatomic,assign) BOOL cadastoConfirmacaoOcorrencia;

@property (weak, nonatomic) IBOutlet UIButton *btLogout;
- (IBAction)salvar:(id)sender;
- (IBAction)logout:(id)sender;

@end
