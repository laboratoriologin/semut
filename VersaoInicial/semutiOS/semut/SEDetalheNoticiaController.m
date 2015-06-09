//
//  SEDetalheNoticiaController.m
//  semut
//
//  Created by Julio Rocha on 29/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEDetalheNoticiaController.h"
#import "SEUtil.h"
#import "MBProgressHUD.h"
#import <Social/Social.h>
#import "SEOcorrenciaDAO.h"
#import "SENoticiaDAO.h"
@interface SEDetalheNoticiaController () {
    BOOL hasImage;
    NSString *urlImage;
    UIImage *image;
    MBProgressHUD *progress;
}

@end

@implementation SEDetalheNoticiaController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    if(_noticia) {
        
        [self initNoticia];
        
    } else {

        [self initOcorrencia];
    
    }
    
    [self initButton];
    

}

-(void) initButton {
    
    if([SEUtil boolForKey:kBoolSucom]) {
        
        _lbTitulo.textColor = [UIColor sucomColor];
        
        [_btCompartilhar setImage:[UIImage imageNamed:@"bt_compartilhar_sucom.png"] forState:UIControlStateNormal];
        
    } else {
        
        _lbTitulo.textColor = [UIColor transalvadorColor];
        
    }
    
    BOOL existe =NO;
    
    if(_ocorrencia) {
        existe = [[[SEOcorrenciaDAO alloc]init]get:_ocorrencia.codigo]!=nil;
    } else {
        existe = [[[SENoticiaDAO alloc]init]get:_noticia.codigo]!=nil;
    }
    
    if(existe) {
        
        [_btSalvar removeTarget:self action:@selector(salvar) forControlEvents:UIControlEventTouchUpInside];
        [_btSalvar addTarget:self action:@selector(remover) forControlEvents:UIControlEventTouchUpInside];
        
        
        if([SEUtil boolForKey:kBoolSucom]) {
            [_btSalvar setImage:[UIImage imageNamed:@"bt_excluir_sucom.png"] forState:UIControlStateNormal];
        } else {
            [_btSalvar setImage:[UIImage imageNamed:@"bt_excluir.png"] forState:UIControlStateNormal];
        }
        
    } else {
      
        [_btSalvar removeTarget:self action:@selector(remover) forControlEvents:UIControlEventTouchUpInside];
        [_btSalvar addTarget:self action:@selector(salvar) forControlEvents:UIControlEventTouchUpInside];
        
        
        if([SEUtil boolForKey:kBoolSucom]) {
            [_btSalvar setImage:[UIImage imageNamed:@"bt_salvar_sucom.png"] forState:UIControlStateNormal];
        } else {
            [_btSalvar setImage:[UIImage imageNamed:@"bt_salvar_transalvador.png"] forState:UIControlStateNormal];
        }
        
    }
    
    
}

-(void) initNoticia {
    
    _lbData.text    = _noticia.data;
    _lbTitulo.text  = _noticia.titulo;
    _lbTexto.text   = _noticia.descricao;

    
    if(_noticia.imagem.length==0) {
        hasImage=NO;

    } else {
        
        hasImage = YES;
        image    = _noticia.iconeOriginal;
        urlImage = _noticia.imagem;
    }
    
    [self adjustScrollViewAndImage];
 
}


-(void) initOcorrencia {
    
    _lbData.text    = _ocorrencia.data;
    _lbTitulo.text  = _ocorrencia.categoria.descricao;
    _lbTexto.text   = _ocorrencia.texto;

    
    if(_ocorrencia.imagem.length==0) {
        hasImage = NO;
        
    } else {
        hasImage = YES;
        image    = _ocorrencia.iconeOriginal;
        urlImage = _ocorrencia.imagem;
    }
    
    [self adjustScrollViewAndImage];
    
}

-(void) adjustScrollViewAndImage {
    
    if(hasImage) {
        
        if(image) {
        
            _imagem.image = image;
            [_imagem setContentMode:UIViewContentModeScaleAspectFit];
        
        } else {
            dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0ul);
            dispatch_async(queue, ^{
                NSURL *url =[NSURL URLWithString:[kUrlImagem stringByAppendingString:urlImage]];
                image = [UIImage imageWithData: [NSData dataWithContentsOfURL:url]];
                dispatch_sync(dispatch_get_main_queue(), ^{
                    [_imagem setImage:image];
                    [_imagem setContentMode:UIViewContentModeScaleAspectFit];
                    [_imagem setNeedsLayout];
                    
                });
            });
        }
  
    } else {
        
        _imagem.alpha=0;
        
        _scrollView.frame = CGRectMake(_imagem.frame.origin.x, _imagem.frame.origin.y, _scrollView.frame.size.width, _imagem.frame.size.height + _scrollView.frame.size.height);
        
    }
  
    [_lbTexto sizeToFit];
    
    _scrollView.contentSize = CGSizeMake(_scrollView.frame.size.width, _lbTexto.frame.size.height);
    
}

- (void)salvar {
    
    BOOL inseriu = NO;
    
    if(_ocorrencia) {
    
        int grupo = [SEUtil boolForKey:kBoolSucom]?kGrupoSucom:kGrupoTransalvador;
        
        inseriu   = [[[SEOcorrenciaDAO alloc]init]inserir:_ocorrencia grupo:grupo];
        
    } else {
        
        int grupo = [SEUtil boolForKey:kBoolSucom]?kGrupoSucom:kGrupoTransalvador;
        
        inseriu   = [[[SENoticiaDAO alloc]init]inserir:_noticia grupo:grupo];
        
    }
    
    progress = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    
    progress.customView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"37x-Checkmark.png"]];
    
    progress.mode = MBProgressHUDModeCustomView;
    
    progress.labelText = inseriu? @"Salvo com sucesso!": @"Já está salvo!";
    
    [progress show:YES];
    
    [progress hide:YES afterDelay:2];
    
    [self initButton];
    
}

-(void) remover {
    
    if(_ocorrencia) {
        
        [[[SEOcorrenciaDAO alloc]init]excluir:_ocorrencia];
        
    } else {
        
        [[[SENoticiaDAO alloc]init] excluir:_noticia];
        
    }
    
    progress = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    
    progress.customView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"37x-Checkmark.png"]];
    
    progress.mode = MBProgressHUDModeCustomView;
    
    progress.labelText = @"Removido!";
    
    [progress show:YES];
    
    [progress hide:YES afterDelay:2];
    
    [self initButton];
    
}

- (IBAction)compartilhar:(id)sender {
    
    UIActionSheet *sheet = [[UIActionSheet alloc]initWithTitle:@"O que deseja?" delegate:self cancelButtonTitle:@"Cancelar" destructiveButtonTitle:nil otherButtonTitles:@"Compartilhar no Facebook",@"Compartilhar no Twitter", nil];
 
    [sheet showInView:self.view];
    
}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
 
    switch (buttonIndex) {
        case 0:
            
            if([SLComposeViewController isAvailableForServiceType:SLServiceTypeFacebook]) {
                [self compartilharFacebook];
                
            } else {
                
                UIAlertView *alertView = [[UIAlertView alloc]initWithTitle:@"Não foi possível compartilhar!"
                                                                   message:@"Verifique sua conta do Facebook no celular ou tente novamente mais tarde"
                                                                  delegate:nil cancelButtonTitle:@"OK" otherButtonTitles: nil];
                
                [alertView show];
                
            }
            
            break;
            
        case 1:
            
            if([SLComposeViewController isAvailableForServiceType:SLServiceTypeTwitter]) {
                [self compartilharTwitter];
                
            } else {
                
                UIAlertView *alertView = [[UIAlertView alloc]initWithTitle:@"Não foi possível compartilhar!"
                                                                   message:@"Verifique sua conta do Twitter no iPhone ou tente novamente mais tarde"
                                                                  delegate:nil cancelButtonTitle:@"OK" otherButtonTitles: nil];
                
                [alertView show];
                
            }
            
            break;
            
        default:
            break;
    }
    
}

-(void) compartilharFacebook {
    
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0ul);
    
    dispatch_async(queue, ^{
       
        dispatch_sync(dispatch_get_main_queue(), ^{
            
            SLComposeViewController *controller = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeFacebook];

            NSString *texto = nil;
            
            if(_noticia) {
                texto = [NSString stringWithFormat:@"%@ - %@",_noticia.titulo,_noticia.descricao];
            } else {
                texto = [NSString stringWithFormat:@"%@ - %@",_ocorrencia.categoria.descricao,_ocorrencia.texto];
            }
            
            [controller setInitialText: texto];
            
            [self presentViewController:controller animated:YES completion:Nil];
            
            
        });
        
    });
    
}

-(void) compartilharTwitter {
    
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0ul);
    
    dispatch_async(queue, ^{
      
        dispatch_sync(dispatch_get_main_queue(), ^{
            
            SLComposeViewController *controller = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeTwitter];
            
            NSString *texto = nil;
            
            if(_noticia) {
                texto = [NSString stringWithFormat:@"%@ - %@",_noticia.titulo,_noticia.descricao];
            } else {
                texto = [NSString stringWithFormat:@"%@ - %@",_ocorrencia.categoria.descricao,_ocorrencia.texto];
            }
            
            [controller setInitialText: texto];
            
            [self presentViewController:controller animated:YES completion:Nil];
            
        });
        
    });
    
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    
}


@end
