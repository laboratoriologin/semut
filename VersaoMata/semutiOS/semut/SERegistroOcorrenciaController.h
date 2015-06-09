//
//  SERegistroOcorrenciaController.h
//  semut
//
//  Created by Julio Rocha on 25/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SECategoria.h"
@interface SERegistroOcorrenciaController : UIViewController <UINavigationControllerDelegate,UITableViewDataSource,UITableViewDelegate,UIActionSheetDelegate,UIImagePickerControllerDelegate,UITextViewDelegate>
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (weak, nonatomic) IBOutlet UISwitch *switchLocalizacaoAtual;

@property(nonatomic,retain) SECategoria *categoriaSelecionada;
@property (weak, nonatomic) IBOutlet UITextView *texto;
- (IBAction)selecionarFoto:(id)sender;
@property (weak, nonatomic) IBOutlet UIImageView *imagemSelecionada;
- (IBAction)removerImagem:(id)sender;
@property (weak, nonatomic) IBOutlet UIButton *btRemover;

- (IBAction)continuar:(id)sender;
@property (weak, nonatomic) IBOutlet UISwitch *switcher;
@property (weak, nonatomic) IBOutlet UILabel *lbRegistroOcorencia;
@property (weak, nonatomic) IBOutlet UIButton *btEnviarImagem;
@property (weak, nonatomic) IBOutlet UIButton *btContinuar;

@end
