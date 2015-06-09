//
//  SETableViewImageCell.h
//  semut
//
//  Created by Julio Rocha on 16/12/13.
//  Copyright (c) 2013 Login. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SETableViewImageCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *lbData;
@property (weak, nonatomic) IBOutlet UIImageView *imgTopo;
@property (weak, nonatomic) IBOutlet UILabel *lbTipoOcorrencia;

@property (weak, nonatomic) IBOutlet UILabel *lbTopo;
@property (weak, nonatomic) IBOutlet UILabel *lbTexto;
@property (weak, nonatomic) IBOutlet UIImageView *imgOcorrencia;
@property (strong, nonatomic) IBOutletCollection(UIView) NSArray *composition;
@property (weak, nonatomic) IBOutlet UILabel *lbProtocolo;

@property (weak, nonatomic) IBOutlet UIImageView *imgPendenteEnvio;
@end
