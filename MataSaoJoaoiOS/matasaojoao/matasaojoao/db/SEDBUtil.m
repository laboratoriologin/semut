//
//  SEDBUtil.m
//  semut
//
//  Created by Julio Rocha on 04/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SEDBUtil.h"
#import "SEAppDelegate.h"
@implementation SEDBUtil

+(FMDatabase *) getConnection {
    
    NSString *databasePath = [(SEAppDelegate *)[[UIApplication sharedApplication] delegate] databasePath];
    FMDatabase *db = [FMDatabase databaseWithPath:databasePath];
    [db open];
    return db;
    
    
}

+(void) closeConnection:(FMDatabase *)conexao {
    
    if(conexao!=nil) {
        [conexao close];
    }
}


@end
