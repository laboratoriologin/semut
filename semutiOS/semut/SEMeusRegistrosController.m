//
//  SEMeusRegistrosController.m
//  semut
//
//  Created by Julio Rocha on 19/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEMeusRegistrosController.h"
#import "SENoticiaDAO.h"
#import "SEOcorrenciaDAO.h"
#import "SEUtil.h"
#import "SETableViewCell.h"
#import "SETableViewImageCell.h"
#import "IconDownloader.h"
#import "SEDetalheNoticiaController.h"
#define kMargemLabel 18

@interface SEMeusRegistrosController () {
    
    NSMutableArray *registros;
    UIColor *colorRef;
    UILabel *labelTitulo;
    UILabel *labelTexto;
    SERegistro *selecionado;
}

@end

@implementation SEMeusRegistrosController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        

    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    colorRef = [SEUtil boolForKey:kBoolSucom]?[UIColor sucomColor]:[UIColor transalvadorColor];
    
}

-(void) viewWillAppear:(BOOL)animated {
    
    [super viewWillAppear:animated];
    
    int grupo = [SEUtil boolForKey:kBoolSucom]?kGrupoSucom:kGrupoTransalvador;
    
    registros = [[[SENoticiaDAO alloc]init] getAllByGrupo:grupo];
    
    [registros addObjectsFromArray:[[[SEOcorrenciaDAO alloc]init] getAllByGrupo:grupo]];
    
    [self.tableView reloadData];
    
    
}

#pragma mark - Table view data source

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath;
{
    
    if([[registros objectAtIndex:indexPath.row]class] == [SEOcorrencia class]) {
        return [self heightForOcorrenciaRowAtIndexPath:indexPath];
    }
    
    SENoticia *noticia = [registros objectAtIndex:indexPath.row];
    
    labelTexto = [[UILabel alloc]initWithFrame:CGRectMake(0,0, 320, 0)];
    
    [labelTexto setText:noticia.titulo];
    
    [labelTexto setNumberOfLines:0];
    
    [labelTexto sizeToFit];
    
    int alturaLabelTitulo = labelTexto.frame.size.height;
    
    return MAX(alturaLabelTitulo +20 , 110);
    
    
}

- (CGFloat) heightForOcorrenciaRowAtIndexPath:(NSIndexPath *)indexPath;
{
    
    labelTexto = [[UILabel alloc]initWithFrame:CGRectMake(0,0, 320, 0)];
    
    SEOcorrencia * ocorrencia = [registros objectAtIndex:indexPath.row];
    
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

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    
    UIView *header  = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 320, 60)];
    
    UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(10, 0, 320, 25)];
    
    [header setBackgroundColor:[UIColor whiteColor]];
    
    [label setText:@"Meus Registros"];
    
    [label setTextColor: [SEUtil boolForKey:kBoolSucom]?[UIColor sucomColor]:[UIColor transalvadorColor]];
    
    [header addSubview:label];
    
    return header;
    
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return registros.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    if([[registros objectAtIndex:indexPath.row] class] == [SENoticia class]) {
    
        SENoticia *noticia = [registros objectAtIndex:indexPath.row];
        
        if (noticia.imagem.length>0)
        {
            return [self cellWithImage:noticia indexPath:indexPath];
            
        }
        
        NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"SETableViewCell" owner:self options:nil];
        
        SETableViewCell *cell = [nib objectAtIndex:0];
        
        cell.lbData.text = noticia.data;
        
        cell.lbTopo.text = noticia.titulo;
        
        cell.lbTexto.text = noticia.descricao;
        
        cell.lbTopo.numberOfLines=0;
        
        for(UIView *comp in cell.composition) {
            comp.backgroundColor = colorRef;
        }
        
        cell.lbTipoOcorrencia.text = [SEUtil boolForKey:kBoolSucom] ? @"Quadro de Avisos" : noticia.educacaoTransito?@"Educação no Trânsito":@"Notícia";
        
        cell.imgTopo.image = [UIImage imageNamed:@"icon-bttrans_noticias.png"];
        
        return cell;
        
    } else {
        
        return [self ocorrenciaCellForRowAtIndexPath:indexPath];
        
    }
    
}

- (UITableViewCell *) ocorrenciaCellForRowAtIndexPath:(NSIndexPath *)indexPath
{

    SEOcorrencia *ocorrencia = [registros objectAtIndex:indexPath.row];
    
    if (ocorrencia.imagem.length>0 || ocorrencia.imagePath.length>0)
    {
        return [self ocorrenciaCellWithImage:ocorrencia indexPath:indexPath];
        
    }
    
    NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"SETableViewCell" owner:self options:nil];
    
    SETableViewCell *cell = [nib objectAtIndex:0];
    
    cell.lbData.text = ocorrencia.data;
    
    cell.lbTopo.text = ocorrencia.categoria.descricao;
    
    cell.lbTexto.text = ocorrencia.texto;
    
    cell.lbTopo.numberOfLines=0;
    
    cell.lbTipoOcorrencia.text = @"Ocorrência";
    
    cell.imgTopo.image = [UIImage imageNamed:@"icon-bttrans_Reg_de_ocor.png"];
    
    if(ocorrencia.pendente==0) {
        cell.imgPendenteEnviando.image = [UIImage imageNamed:@"icone_check_enviado.png"];
        cell.lbProtocolo.text = [NSString stringWithFormat:@"NP:%ld",ocorrencia.codigo];
        [cell.lbProtocolo setTextColor: [SEUtil boolForKey:kBoolSucom]?[UIColor sucomColor]:[UIColor transalvadorColor]];        
    } else {
        cell.imgPendenteEnviando.image = [UIImage imageNamed:@"icone_enviando.png"];
    }
    
    for(UIView *comp in cell.composition) {
        comp.backgroundColor = colorRef;
    }
    
    return cell;
    
}

- (UITableViewCell *) cellWithImage:(SENoticia *) noticia indexPath:(NSIndexPath *) indexPath {
    
    NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"SETableViewImageCell" owner:self options:nil];
    
    SETableViewImageCell *cell = [nib objectAtIndex:0];
    
    cell.lbData.text = noticia.data;
    
    cell.lbTopo.text = noticia.titulo;
    
    cell.lbTexto.text = noticia.descricao;
    
    cell.lbTopo.numberOfLines=0;
    
    [cell.lbTopo sizeToFit];
    
    for(UIView *comp in cell.composition) {
        comp.backgroundColor = colorRef;
    }
    
    if (!noticia.icone && noticia.imagem.length>0)
    {
        
        cell.imgOcorrencia.image = [UIImage imageNamed:@"placeholder.jpg"];
        
        if (self.tableView.dragging == NO && self.tableView.decelerating == NO)
        {
            [self startIconDownload:noticia forIndexPath:indexPath];
        }
        
    }
    else
    {
        
        cell.imgOcorrencia.image = noticia.icone;
        
        [cell.imgOcorrencia setNeedsLayout];
        
    }
    
    cell.lbTipoOcorrencia.text = [SEUtil boolForKey:kBoolSucom] ? @"Quadro de Avisos" : noticia.educacaoTransito?@"Educação no Trânsito":@"Notícia";
    
    cell.imgTopo.image = [UIImage imageNamed:@"icon-bttrans_noticias.png"];
    
    return cell;
    
}

- (UITableViewCell *) ocorrenciaCellWithImage:(SEOcorrencia *) ocorrencia indexPath:(NSIndexPath *) indexPath {
    
    NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"SETableViewImageCell" owner:self options:nil];
    
    SETableViewImageCell *cell = [nib objectAtIndex:0];
    
    cell.lbData.text = ocorrencia.data;
    
    cell.lbTopo.text = ocorrencia.categoria.descricao;
    
    cell.lbTopo.numberOfLines=0;
    
    cell.lbTexto.text = ocorrencia.texto;
    
    [cell.lbTopo sizeToFit];
    
    for(UIView *comp in cell.composition) {
        comp.backgroundColor = colorRef;
    }
    
    if(ocorrencia.imagePath.length>0) {
        
        ocorrencia.icone = [UIImage imageWithContentsOfFile:ocorrencia.imagePath];
        
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
    
    cell.lbTipoOcorrencia.text = @"Ocorrência";
    
    cell.imgTopo.image = [UIImage imageNamed:@"icon-bttrans_Reg_de_ocor.png"];
    
    if(ocorrencia.pendente==0) {
        cell.imgPendenteEnvio.image = [UIImage imageNamed:@"icone_check_enviado.png"];
        cell.lbProtocolo.text = [NSString stringWithFormat:@"NP:%ld",ocorrencia.codigo];
        [cell.lbProtocolo setTextColor: [SEUtil boolForKey:kBoolSucom]?[UIColor sucomColor]:[UIColor transalvadorColor]];
    } else {
        cell.imgPendenteEnvio.image = [UIImage imageNamed:@"icone_enviando.png"];
    }
    
    return cell;
    
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    selecionado = [registros objectAtIndex:indexPath.row];
    
    [self performSegueWithIdentifier:@"sg_detalhe_noticia" sender:nil];
    
}

#pragma mark icondownloader

- (void)loadImagesForOnscreenRows
{
    if ([registros count] > 0)
    {
        NSArray *visiblePaths = [self.tableView indexPathsForVisibleRows];
        for (NSIndexPath *indexPath in visiblePaths)
        {
            SERegistro *registro = [registros objectAtIndex:indexPath.row];
            
            if (!registro.icone && registro.imagem.length>0)
            {
                [self startIconDownload:registro forIndexPath:indexPath];
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


#pragma mark - Navigation

// In a story board-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    SEDetalheNoticiaController *controller = segue.destinationViewController;
    
    if([selecionado class]==[SENoticia class]) {
        controller.noticia = (SENoticia *)selecionado;
    } else {
        controller.ocorrencia = (SEOcorrencia *)selecionado;
    }
    
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];

}

@end
