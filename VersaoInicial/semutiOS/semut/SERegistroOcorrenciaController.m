//
//  SERegistroOcorrenciaController.m
//  semut
//
//  Created by Julio Rocha on 25/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SERegistroOcorrenciaController.h"
#import "SECategoriasController.h"
#import "SEUtil.h"
#import "MBProgressHUD.h"
#import "SEMarcacaoMapaController.h"
#import "SEConclusaoController.h"
#import "SEAppDelegate.h"
#define  kDigiteAqui @"Digite aqui sua ocorrência"
@interface SERegistroOcorrenciaController () {
    NSData *imageUpload;
    MBProgressHUD *progress;
}

@end

@implementation SERegistroOcorrenciaController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {

    }
    return self;
}

#pragma mark - initial configuration

- (void)viewDidLoad
{
    [super viewDidLoad];
    _categoriaSelecionada = [[SECategoria alloc]init];
	_tableView.delegate=self;
    _tableView.dataSource=self;
    _texto.delegate=self;
    if([SEUtil boolForKey:kBoolSucom]) {
        [self initColorSucom];
    }
    
}

-(void) initColorSucom {
    
    _lbRegistroOcorencia.textColor = [UIColor colorFromHexString:kCorSucom];
    
    _switcher.onTintColor = [UIColor colorFromHexString:kCorSucom];
    
    [_btEnviarImagem setTitleColor:[UIColor colorFromHexString:kCorSucom] forState:UIControlStateNormal];
    
    [_btContinuar setTitleColor:[UIColor colorFromHexString:kCorSucom] forState:UIControlStateNormal];
    
}



-(void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [_tableView reloadData];
    _texto.backgroundColor = [UIColor colorFromHexString:@"#EEE9E9"];
}


#pragma mark - UITableViewDataSource and Delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 1;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    UITableViewCell *cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:nil];
    
    cell.textLabel.text = @"Categoria";
    
    cell.backgroundColor = [UIColor lightTextColor];
    
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    
    cell.detailTextLabel.text = _categoriaSelecionada.descricao;
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    
    [self performSegueWithIdentifier:@"sg_detalhe_categoria" sender:nil];
    
    
}

#pragma mark - TextView delegate

-(void) touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    
    if([_texto isFirstResponder] ) {
        
        [_texto resignFirstResponder];
        
        if(_texto.text.length==0) {
        
            _texto.text = kDigiteAqui;

        }
        
    }
    
}

- (BOOL)textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text {
    
    if([@"\n" isEqualToString:text]) {
        
        [_texto resignFirstResponder];
        
        if(_texto.text.length==0) {
            _texto.text =  kDigiteAqui;
        }
        
        return NO;
    }
    
    return YES;
}

- (BOOL)textViewShouldBeginEditing:(UITextView *)textView {
    
    if([kDigiteAqui isEqualToString:_texto.text]) {
        _texto.text = @"";
    }
    
    return YES;
    
}

-(BOOL) textViewShouldReturn:(UITextView *)textView{
    
    [_texto resignFirstResponder];
    
    if(_texto.text.length==0) {
        _texto.text =  kDigiteAqui;
    }
    
    return YES;
    
}

#pragma mark Selecionar Foto

- (IBAction)selecionarFoto:(id)sender {
    
    UIActionSheet *menu = [[UIActionSheet alloc]initWithTitle:@"Capturar imagem usando:" delegate:self cancelButtonTitle:@"Cancelar" destructiveButtonTitle:nil otherButtonTitles:@"Câmera",@"Biblioteca", nil];
    
    [menu showInView:self.view];
    
    
}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    
    UIImagePickerController *picker = [[UIImagePickerController alloc]init];
    
    if(buttonIndex==2) {
        return;
    }
    
    if(buttonIndex==0) {
        
        if (![UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
            [[[UIAlertView alloc]initWithTitle:@"OPS!" message:@"Não foi possível encontrar uma câmera no seu dispositivo!" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil]show];
            return;
        }
        
        picker.sourceType = UIImagePickerControllerSourceTypeCamera;
        
    } else if(buttonIndex==1) {
        
        picker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
        
    }
    
    picker.delegate=self;
    
    [self presentViewController:picker animated:YES completion:nil];
    
}

- (void)navigationController:(UINavigationController *)navigationController willShowViewController:(UIViewController *)viewController animated:(BOOL)animated
{
    [viewController.navigationItem setTitle:@""];
    [viewController.navigationController.navigationBar setTintColor:[UIColor blackColor]];
}

- (IBAction)removerImagem:(id)sender {
    
    imageUpload=nil;
    _imagemSelecionada.image = [[UIImage alloc]init];
    _btRemover.alpha=0.0;
    
}

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info
{
    UIImage *image = [info valueForKey:UIImagePickerControllerOriginalImage];
    
    _imagemSelecionada.image= image;
    
    _imagemSelecionada.contentMode = UIViewContentModeScaleAspectFit;
    
    progress = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    
    progress.mode = MBProgressHUDModeIndeterminate;
    
    progress.labelText=@"Processando imagem...";
    
    if(image.size.width>2048 || image.size.height > 1400) {
        
        [progress showAnimated:YES whileExecutingBlock:^ {
            CGSize size = CGSizeMake(image.size.width/4, image.size.height/4);
            
            UIGraphicsBeginImageContext(size );
            
            [image drawInRect:CGRectMake(0,0,size.width,size.height)];
            
            UIImage* newImage = UIGraphicsGetImageFromCurrentImageContext();
            
            UIGraphicsEndImageContext();
            
            imageUpload = UIImageJPEGRepresentation(newImage, 0.2);
        }];
        
    } else if(image.size.width>1024 || image.size.height>768) {
        
        [progress showAnimated:YES whileExecutingBlock:^ {
            
            CGSize size = CGSizeMake(image.size.width/2, image.size.height/2);
            
            UIGraphicsBeginImageContext(size );
            
            [image drawInRect:CGRectMake(0,0,size.width,size.height)];
            
            UIImage* newImage = UIGraphicsGetImageFromCurrentImageContext();
            
            UIGraphicsEndImageContext();
            
            imageUpload = UIImageJPEGRepresentation(newImage, 0.2);
            
        }];
        
    } else {
        
        imageUpload = UIImageJPEGRepresentation(image, 0.2);
        
        [progress hide:YES];
        
    }
    
    _imagemSelecionada.backgroundColor = [UIColor clearColor];
    
    [picker dismissViewControllerAnimated:YES completion:nil];
    
    picker = nil;
    
    _btRemover.alpha=1.0;
    
    
}


- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker
{
    [self dismissViewControllerAnimated:YES completion:NULL];
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];

}

- (IBAction)continuar:(id)sender {
    
    if(_categoriaSelecionada.codigo==0) {
        
        [[[UIAlertView alloc]initWithTitle:@"" message:@"Selecione uma categoria" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles: nil]show];
        
    } else {
        
        SEAppDelegate *delegate = (SEAppDelegate *) [[UIApplication sharedApplication] delegate];
        
        if([_switcher isOn] && delegate.latitude!=0) {
            [self performSegueWithIdentifier:@"sg_conclusao" sender:nil];
        } else {
            [self performSegueWithIdentifier:@"sg_marcacao_mapa" sender:nil];
        }

    }
    
}


-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    SEOcorrencia *ocorrencia = [[SEOcorrencia alloc]init];
    
    ocorrencia.categoria = _categoriaSelecionada;
    
    if(![kDigiteAqui isEqualToString:_texto.text]) {
    
        ocorrencia.texto = _texto.text;
        
    }
    
    if(imageUpload) {
        
        ocorrencia.imageData = imageUpload;
        
    }
    
    if([@"sg_marcacao_mapa" isEqualToString:segue.identifier]) {
        
        SEMarcacaoMapaController *controller = segue.destinationViewController;
        
        controller.ocorrencia = ocorrencia;
        
    } else if([@"sg_conclusao" isEqualToString:segue.identifier]) {
        
        SEAppDelegate *delegate = (SEAppDelegate *) [[UIApplication sharedApplication] delegate];
        
        ocorrencia.latitude = delegate.latitude;
        
        ocorrencia.longitude = delegate.longitude;
        
        SEConclusaoController *controller = segue.destinationViewController;
        
        controller.ocorrencia = ocorrencia;
    
    } else {
    
        SECategoriasController * controller = segue.destinationViewController;
        
        controller.categoria = _categoriaSelecionada;
        
    }
    
}


@end
