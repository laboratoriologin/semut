//
//  SEAlertasController.m
//  semut
//
//  Created by Julio Rocha on 10/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEAlertasController.h"
#import "SEAlertaBS.h"
#import "SEOcorrencia.h"
#import "IconDownloader.h"
#import "SEDetalheNoticiaController.h"
#import <QuartzCore/QuartzCore.h>
#import "SETableViewCell.h"
#import "SETableViewImageCell.h"
#import "SEAppDelegate.h"
#define kMargemLabel 18
@interface SEAlertasController () {
    NSMutableArray *alertas;
    UIActivityIndicatorView *uiBusy;
    UILabel *labelTexto;
    SEOcorrencia *selecionada;
}

@end

@implementation SEAlertasController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    SEAppDelegate *delegate = (SEAppDelegate *) [[UIApplication sharedApplication]delegate];
    
    if(!delegate.internetAvailable) {
        
        [[[UIAlertView alloc]initWithTitle:@"Sem conexão" message:@"Verifique sua conexão com a internet" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil,nil]show];
        
    } else if(!delegate.transferingData) {
        
        [[[UIAlertView alloc]initWithTitle:@"Sem conexão" message:@"Não foi possível carregar as informações" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil,nil]show];
        
    }
    
    [self findAlertas];

}

-(void) findAlertas {
    
    uiBusy = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhite];
    
    uiBusy.hidesWhenStopped = YES;
    
    [uiBusy startAnimating];
    
    UIBarButtonItem *item = [[UIBarButtonItem alloc]initWithCustomView:uiBusy];
    
    self.navigationItem.rightBarButtonItem=item;

    alertas = [[NSMutableArray alloc]init];
    
    SEAlertaBS *alertaBS = [[SEAlertaBS alloc] init];
    
    [alertaBS setCompletionHandler:^{
        
        [self.tableView reloadData];
        
        self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]initWithBarButtonSystemItem:UIBarButtonSystemItemRefresh target:self action:@selector(findAlertas)];
        
        
    }];
    
    [alertaBS findAlertas:alertas];
}

#pragma mark - Table view data source

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    
    UIView *header  = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 320, 60)];
    
    UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(10, 0, 320, 25)];
    
    [header setBackgroundColor:[UIColor whiteColor]];
    
    [label setText:@"Alertas"];
    
    [label setTextColor:[UIColor transalvadorColor]];
    
    [label setFont:[UIFont systemFontOfSize:17.0]];
    
    [header addSubview:label];
    
    return header;
    
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath;
{
   
    labelTexto = [[UILabel alloc]initWithFrame:CGRectMake(0,0, 320, 0)];
    
    SEOcorrencia * ocorrencia = [alertas objectAtIndex:indexPath.row];
    
    [labelTexto setText:[NSString stringWithFormat:@"%@ \n %@",ocorrencia.data,ocorrencia.categoria.descricao]];
    
    [labelTexto setNumberOfLines:0];
    
    [labelTexto sizeToFit];
    
    int alturaLabelTitulo = labelTexto.frame.size.height;
    
    labelTexto = [[UILabel alloc]initWithFrame:CGRectMake(0,0, 320, 0)];
    
    [labelTexto setText:ocorrencia.texto];
    
    [labelTexto setNumberOfLines:0];
    
    [labelTexto sizeToFit];
    
    int alturaLabelTexto = labelTexto.frame.size.height;
    
    return MAX(alturaLabelTexto + alturaLabelTitulo + kMargemLabel,100 + kMargemLabel);
    
    
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return alertas.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{

    SEOcorrencia *ocorrencia = [alertas objectAtIndex:indexPath.row];
    
    if (ocorrencia.imagem.length>0)
    {
        return [self cellWithImage:ocorrencia indexPath:indexPath];
        
    }
    
    NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"SETableViewCell" owner:self options:nil];
    
    SETableViewCell *cell = [nib objectAtIndex:0];
    
    cell.lbData.text = ocorrencia.data;
    
    cell.lbTopo.text = ocorrencia.categoria.descricao;
    
    cell.lbTopo.numberOfLines=0;
    
    [cell.lbTopo sizeToFit];
    
    cell.lbTexto.text =ocorrencia.texto;
    
    [cell.lbTexto setNumberOfLines:0];
    
    [cell.lbTexto sizeToFit];
    
    for(UIView *comp in cell.composition) {
        comp.backgroundColor = [UIColor transalvadorColor];
    }

    return cell;

}

- (UITableViewCell *) cellWithImage:(SEOcorrencia *) ocorrencia indexPath:(NSIndexPath *) indexPath {
    
    NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"SETableViewImageCell" owner:self options:nil];
    
    SETableViewImageCell *cell = [nib objectAtIndex:0];
    
    cell.lbData.text = ocorrencia.data;
    
    cell.lbTopo.text = ocorrencia.categoria.descricao;
    
    cell.lbTopo.numberOfLines=0;
    
    [cell.lbTopo sizeToFit];
    
    cell.lbTexto.text =ocorrencia.texto;
    
    [cell.lbTexto setNumberOfLines:0];
    
    [cell.lbTexto sizeToFit];    

    for(UIView *comp in cell.composition) {
        comp.backgroundColor = [UIColor transalvadorColor];
    }
    
    if (!ocorrencia.icone && ocorrencia.imagem.length>0)
    {
        
        cell.imgOcorrencia.image = [UIImage imageNamed:@"placeholder.jpg"];
        
        if (self.tableView.dragging == NO && self.tableView.decelerating == NO)
        {
            [self startIconDownload:ocorrencia forIndexPath:indexPath];
        }
    }
    else
    {
        
        cell.imgOcorrencia.image = ocorrencia.icone;
        
        [cell.imgOcorrencia setNeedsLayout];
        
    }
    
    return cell;
    
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    selecionada = [alertas objectAtIndex:indexPath.row];
    
    [self performSegueWithIdentifier:@"sg_detalhe_noticia" sender:nil];
    
}

#pragma mark icondownloader

- (void)loadImagesForOnscreenRows
{
    if ([alertas count] > 0)
    {
        NSArray *visiblePaths = [self.tableView indexPathsForVisibleRows];
        for (NSIndexPath *indexPath in visiblePaths)
        {
            SEOcorrencia *ocorrencia = [alertas objectAtIndex:indexPath.row];
            
            if (!ocorrencia.icone && ocorrencia.imagem.length>0)
            {
                [self startIconDownload:ocorrencia forIndexPath:indexPath];
            }
        }
    }
}

- (void)startIconDownload:(SERegistro *)registro forIndexPath:(NSIndexPath *)indexPath
{
    IconDownloader *iconDownloader = [self.imageDownloadsInProgress objectForKey:indexPath];
    if (iconDownloader == nil)
        
    {
        
        iconDownloader = [[IconDownloader alloc] init];
        
        iconDownloader.registro = registro;
        
        [iconDownloader setCompletionHandler:^{
            
            SETableViewImageCell *cell = (SETableViewImageCell *)[self.tableView cellForRowAtIndexPath:indexPath];
            
            cell.imgOcorrencia.image = registro.icone;
            
            [self.imageDownloadsInProgress removeObjectForKey:indexPath];
            
            [cell.imgOcorrencia setNeedsLayout];
            
        }];
        
        [self.imageDownloadsInProgress setObject:iconDownloader forKey:indexPath];
        
        [iconDownloader startDownload];
        
    }
}

// -------------------------------------------------------------------------------
//  scrollViewDidEndDragging:willDecelerate:
//  Load images for all onscreen rows when scrolling is finished.
// -------------------------------------------------------------------------------
- (void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate
{
    
    if (!decelerate) {
        [self loadImagesForOnscreenRows];
    }
    
}

-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    SEDetalheNoticiaController *controller = [segue destinationViewController];
    
    controller.ocorrencia = selecionada;
    
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    
}


@end
