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
#import "MWPhotoBrowser.h"
@interface SEDetalheNoticiaController : UIViewController <UIActionSheetDelegate,MWPhotoBrowserDelegate>

@property (weak, nonatomic) IBOutlet UILabel *lbData;
@property (weak, nonatomic) IBOutlet UILabel *lbTitulo;
@property (weak, nonatomic) IBOutlet UILabel *lbTexto;
@property (weak, nonatomic) IBOutlet UIImageView *imagem;
@property (weak, nonatomic) IBOutlet UIButton *btSalvar;
@property (weak, nonatomic) IBOutlet UIButton *btCompartilhar;
@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic) IBOutlet UILabel *lbProtocolo;
- (IBAction)compartilhar:(id)sender;

@property SENoticia *noticia;
@property SEOcorrencia *ocorrencia;

@end
