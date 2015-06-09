//
//  SEDBUtil.h
//  semut
//
//  Created by Julio Rocha on 04/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "FMDatabase.h"
@interface SEDBUtil : NSObject

+(FMDatabase *) getConnection;

+(void) closeConnection:(FMDatabase *)conexao;
@end
