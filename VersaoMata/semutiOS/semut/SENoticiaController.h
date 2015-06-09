//
//  SENoticiaController.h
//  semut
//
//  Created by Julio Rocha on 27/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SENoticiaController : UITableViewController

@property(nonatomic,retain) NSMutableArray * noticias;

@property (nonatomic) NSMutableDictionary *imageDownloadsInProgress;

-(void) findNoticias;

@end
