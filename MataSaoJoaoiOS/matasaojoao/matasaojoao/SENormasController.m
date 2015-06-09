//
//  SENormasController.m
//  semut
//
//  Created by Julio Rocha on 28/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SENormasController.h"

@interface SENormasController ()

@end

@implementation SENormasController

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
    [_norma sizeToFit];
    _scrollView.contentSize = CGSizeMake(_scrollView.bounds.size.width, _norma.bounds.size.height);
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
