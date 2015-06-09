//
//  SETelefonesUteisController.m
//  semut
//
//  Created by Julio Rocha on 05/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SETelefonesUteisController.h"
#define kTelefoneSamu @"tel:192"
#define kTelefoneBombeiros @"tel:193"
#define kTelefoneDefesaCivil @"tel:199"
#define kTelefonePoliciaMilitar @"tel:190"



@interface SETelefonesUteisController ()

@end

@implementation SETelefonesUteisController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {

    }
    return self;

    self.clearsSelectionOnViewWillAppear=YES;
    
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {

    switch (indexPath.row) {
        case 0:
            [[UIApplication sharedApplication]openURL:[NSURL URLWithString:kTelefoneBombeiros]];
            break;
            
        case 1:
            [[UIApplication sharedApplication]openURL:[NSURL URLWithString:kTelefoneDefesaCivil]];
            break;
        case 2:
            [[UIApplication sharedApplication]openURL:[NSURL URLWithString:kTelefonePoliciaMilitar]];
            break;
        case 3:
            [[UIApplication sharedApplication]openURL:[NSURL URLWithString:kTelefoneSamu]];
            break;
    }
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    
}

@end
