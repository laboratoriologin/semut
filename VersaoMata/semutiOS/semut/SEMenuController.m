//
//  SEMenuController.m
//  semut
//
//  Created by Julio Rocha on 25/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEMenuController.h"
#import "SEUtil.h"
@interface SEMenuController ()

@end

@implementation SEMenuController

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
	
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
  
}

- (IBAction)faleComTransalvador:(id)sender {
    UIApplication *myApp = [UIApplication sharedApplication];
    [myApp openURL:[NSURL URLWithString:kTelefoneTransalvador]];
    
}

-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    if([segue.identifier isEqualToString:@"sg_educacao_transito"]) {
        [SEUtil setBool:YES forKey:kBoolEducacaoTransito];
    }
    
}

@end
