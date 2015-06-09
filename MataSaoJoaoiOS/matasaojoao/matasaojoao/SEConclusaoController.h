//
//  SEConclusaoController.h
//  semut
//
//  Created by Julio Rocha on 26/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SEOcorrencia.h"
@interface SEConclusaoController : UIViewController <UIAlertViewDelegate>

@property(nonatomic,retain) SEOcorrencia *ocorrencia;
@property (strong, nonatomic) IBOutletCollection(UILabel) NSArray *labels;
- (IBAction)confirmar:(id)sender;
@property (weak, nonatomic) IBOutlet UILabel *lbNome;
@property (weak, nonatomic) IBOutlet UILabel *lbData;
@property (weak, nonatomic) IBOutlet UILabel *lbHora;
@property (weak, nonatomic) IBOutlet UILabel *lbCategoriaOcorrencia;
@property (weak, nonatomic) IBOutlet UILabel *lbTexto;
@property (weak, nonatomic) IBOutlet UIImageView *imagem;

@property (weak, nonatomic) IBOutlet UIButton *btConfirmar;
@end
