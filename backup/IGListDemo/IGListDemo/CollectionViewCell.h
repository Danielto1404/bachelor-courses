//
//  CollectionViewCell.h
//  IGListDemo
//
//  Created by Oleg Adamov on 11.11.2019.
//  Copyright © 2019 Odnoklssniki Ltd. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "User.h"

NS_ASSUME_NONNULL_BEGIN

@interface CollectionViewCell : UICollectionViewCell

- (void)updateWithUser:(User *)user;

+ (void)calculateHeightForUser:(User *)user;

@end

NS_ASSUME_NONNULL_END
