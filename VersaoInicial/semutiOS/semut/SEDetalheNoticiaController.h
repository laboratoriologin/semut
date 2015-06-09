//
//  SEDetalheNoticiaController.h
//  semut
//
//  Created by Julio Rocha on 29/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SENoticia.h"
#import "SEOcorrencia.h"
@interface SEDetalheNoticiaController : UIViewController <UIActionSheetDelegate>

@property (weak, nonatomic) IBOutlet UILabel *lbData;
@property (weak, nonatomic) IBOutlet UILabel *lbTitulo;
@property (weak, nonatomic) IBOutlet UILabel *lbTexto;
@property (weak, nonatomic) IBOutlet UIImageView *imagem;
@property (weak, nonatomic) IBOutlet UIButton *btSalvar;
@property (weak, nonatomic) IBOutlet UIButton *btCompartilhar;
@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
- (IBAction)compartilhar:(id)sender;

@property SENoticia *noticia;
@property SEOcorrencia *ocorrencia;

@end
