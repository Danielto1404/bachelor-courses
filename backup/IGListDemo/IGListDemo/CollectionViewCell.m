//
//  CollectionViewCell.m
//  IGListDemo
//
//  Created by Oleg Adamov on 11.11.2019.
//  Copyright © 2019 Odnoklssniki Ltd. All rights reserved.
//

#import "CollectionViewCell.h"

@interface CollectionViewCell ()

@property (strong) UILabel *nameLabel;

@end

@implementation CollectionViewCell

- (instancetype)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    
    self.backgroundColor = UIColor.systemBackgroundColor;
    self.nameLabel = [[UILabel alloc] initWithFrame:self.contentView.bounds];
    self.nameLabel.textAlignment = NSTextAlignmentLeft;
    self.nameLabel.numberOfLines = 0;
    [self.contentView addSubview:self.nameLabel];
    
    return self;
}

- (void)updateWithUser:(User *)user {
    self.nameLabel.text = [NSString stringWithFormat:@"%@ \n %lu", user.name, (unsigned long)user.age];
}

+ (void)calculateHeightForUser:(User *)user {
    user.calculatedHeight = user.age * 2 + 40;
}

@end
