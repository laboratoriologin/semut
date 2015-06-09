//
//  SETableViewCell.m
//  semut
//
//  Created by Julio Rocha on 16/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import "SETableViewCell.h"

@implementation SETableViewCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
