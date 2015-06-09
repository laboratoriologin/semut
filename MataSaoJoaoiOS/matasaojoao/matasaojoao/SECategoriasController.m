//
//  SECategoriasController.m
//  semut
//
//  Created by Julio Rocha on 25/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SECategoriasController.h"
#import "SECategoriaBS.h"
#import "SEUtil.h"
@interface SECategoriasController () {
    NSMutableArray *categorias;
}

@end

@implementation SECategoriasController

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
    
    SECategoriaBS *categoriaBS = [[SECategoriaBS alloc]init];
    
    if([SEUtil boolForKey:kBoolSucom]) {
        
        categorias = [categoriaBS findCategoriasSucom];
        
    } else {
    
        categorias = [categoriaBS findCategoriasTransalvador];
        
    }

    [self.tableView registerClass:[UITableViewCell class] forCellReuseIdentifier:@"Cell"];

}


#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{

    return categorias.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    SECategoria *categoriaCorrente = [categorias objectAtIndex:indexPath.row];
    
    if(_categoria.codigo == categoriaCorrente.codigo) {
    
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
    
    }
    
    cell.textLabel.text = categoriaCorrente.descricao;
    
    return cell;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    
    SECategoria *selecionada = [categorias objectAtIndex:indexPath.row];
    
    _categoria.codigo = selecionada.codigo;
  
    _categoria.descricao = selecionada.descricao;
    
    [self.navigationController popViewControllerAnimated:YES];
    
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];

}

@end
