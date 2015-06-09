//
//  SENoticiaController.m
//  semut
//
//  Created by Julio Rocha on 27/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SENoticiaController.h"
#import "SENoticiaBS.h"
#import "SENoticia.h"
#import "SEUtil.h"
#import "IconDownloader.h"
#import "SEDetalheNoticiaController.h"
#import <QuartzCore/QuartzCore.h>
#import "SETableViewCell.h"
#import "SETableViewImageCell.h"
@interface SENoticiaController () {
    UIActivityIndicatorView *uiBusy;
    SENoticia *noticiaSelecionada;
    UILabel *labelTexto;
    UIColor *colorRef;
    NSString *txtTopo;
    BOOL educacaoTransito;
    
}

@end

@implementation SENoticiaController

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
    
    [self.tableView registerNib:[UINib nibWithNibName:@"SETableViewCell" bundle:nil] forCellReuseIdentifier:@"SETableViewCell"];
    
    [self.tableView registerNib:[UINib nibWithNibName:@"SETableViewImageCell" bundle:nil] forCellReuseIdentifier:@"SETableViewImageCell"];
    
    [self findNoticias];
    
}

-(void) findNoticias {
    
    _noticias = [[NSMutableArray alloc]init];
    
    SENoticiaBS * noticiaBS = [[SENoticiaBS alloc]init];
    
    uiBusy = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhite];
    
    uiBusy.hidesWhenStopped = YES;
    
    [uiBusy startAnimating];
    
    UIBarButtonItem *item = [[UIBarButtonItem alloc]initWithCustomView:uiBusy];
    
    self.navigationItem.rightBarButtonItem=item;
    
    [noticiaBS setCompletionHandler:^{
        
        [self.tableView reloadData];
        
        self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]initWithBarButtonSystemItem:UIBarButtonSystemItemRefresh target:self action:@selector(findNoticias)];
        
    }];

    
    if([SEUtil boolForKey:kBoolSucom]) {
       
        colorRef = [UIColor sucomColor];
        
        txtTopo = @"Quadro de avisos";
        
        [noticiaBS findNoticiasSucom:_noticias];
        
    } else {
        
        colorRef = [UIColor transalvadorColor];
        
        txtTopo = @"Notícias";
        
        educacaoTransito = [SEUtil boolForKey:kBoolEducacaoTransito];
        
        [SEUtil setBool:NO forKey:kBoolEducacaoTransito];
        
        if(educacaoTransito) {
            [noticiaBS findNoticiasTransalvadorEducacao:_noticias];
            txtTopo = @"Educação no Trânsito";
        } else {
            [noticiaBS findNoticiasTransalvador:_noticias];
        }
        
    }
    
}

#pragma mark - Table view data source

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    
    UIView *header  = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 320, 60)];
    
    UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(10, 0, 320, 25)];
    
    [header setBackgroundColor:[UIColor whiteColor]];
    
    [label setText:txtTopo];
    
    [label setTextColor:colorRef];
    
    [label setFont:[UIFont systemFontOfSize:17.0]];
    
    [header addSubview:label];
    
    return header;
    
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath;
{
    
    labelTexto = [[UILabel alloc]initWithFrame:CGRectMake(0,0, 320, 0)];
    
    SENoticia * noticia = [_noticias objectAtIndex:indexPath.row];
    
    [labelTexto setText:noticia.titulo];
    
    [labelTexto setNumberOfLines:0];
    
    [labelTexto sizeToFit];
    
    int alturaLabelTitulo = labelTexto.frame.size.height;
    
    return MAX(alturaLabelTitulo +20 , 110);
    
    
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{

    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _noticias.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    SENoticia *noticia = [_noticias objectAtIndex:indexPath.row];
    
    if (noticia.imagem.length>0)
    {
        return [self cellWithImage:noticia indexPath:indexPath];
        
    }
    
    NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"SETableViewCell" owner:self options:nil];
    
    SETableViewCell *cell = [nib objectAtIndex:0];
    
    cell.lbData.text = noticia.data;
    
    cell.lbTopo.text = noticia.titulo;
    
    cell.lbTopo.numberOfLines=0;
    
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
    
    return cell;
    
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    noticiaSelecionada = [_noticias objectAtIndex:indexPath.row];
    
    [self performSegueWithIdentifier:@"sg_detalhe_noticia" sender:nil];
    
}

#pragma mark icondownloader

- (void)loadImagesForOnscreenRows
{
    if ([_noticias count] > 0)
    {
        NSArray *visiblePaths = [self.tableView indexPathsForVisibleRows];
        for (NSIndexPath *indexPath in visiblePaths)
        {
            SENoticia *noticia = [_noticias objectAtIndex:indexPath.row];

            if (!noticia.icone && noticia.imagem.length>0)
            {
                [self startIconDownload:noticia forIndexPath:indexPath];
            }
        }
    }
}

- (void)startIconDownload:(SENoticia *)noticia forIndexPath:(NSIndexPath *)indexPath
{
    IconDownloader *iconDownloader = [self.imageDownloadsInProgress objectForKey:indexPath];
    if (iconDownloader == nil)
        
    {
        
        iconDownloader = [[IconDownloader alloc] init];
        
        iconDownloader.registro = noticia;
        
        [iconDownloader setCompletionHandler:^{
            
            SETableViewImageCell *cell = (SETableViewImageCell *)[self.tableView cellForRowAtIndexPath:indexPath];
            
            cell.imgOcorrencia.image = noticia.icone;
            
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
    
    noticiaSelecionada.educacaoTransito = educacaoTransito;
    
    controller.noticia = noticiaSelecionada;
    
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    
}

@end
