//
//  SEMenuSucomController.m
//  semut
//
//  Created by Julio Rocha on 05/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEMenuSucomController.h"
#import "SEUtil.h"
@interface SEMenuSucomController ()

@end

@implementation SEMenuSucomController

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

- (IBAction)faleComSucom:(id)sender {
    UIApplication *myApp = [UIApplication sharedApplication];
    [myApp openURL:[NSURL URLWithString:kTelefoneSucom]];
}

- (IBAction)faleComPoluicaoSonora:(id)sender {
    UIApplication *myApp = [UIApplication sharedApplication];
    [myApp openURL:[NSURL URLWithString:kTelefonePoluicaoSonora]];
}
@end
