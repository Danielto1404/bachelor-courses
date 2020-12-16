//
//  SectionController.m
//  IGListDemo
//
//  Created by Oleg Adamov on 11.11.2019.
//  Copyright © 2019 Odnoklssniki Ltd. All rights reserved.
//

#import "SectionController.h"
#import "User.h"
#import "CollectionViewCell.h"

@interface SectionController ()

@property (nonatomic) User *user;

@end

@implementation SectionController

- (instancetype)init {
    self = [super init];
    self.inset = UIEdgeInsetsMake(4.f, 0.f, 8.f, 0.f);
    return self;
}

- (CGSize)sizeForItemAtIndex:(NSInteger)index {
    [CollectionViewCell calculateHeightForUser:self.user];
    return CGSizeMake(self.collectionContext.containerSize.width, self.user.calculatedHeight);
}

- (UICollectionViewCell *)cellForItemAtIndex:(NSInteger)index {
    CollectionViewCell *cell = [self.collectionContext dequeueReusableCellOfClass:CollectionViewCell.class forSectionController:self atIndex:index];
    [cell updateWithUser:self.user];
    return cell;
}

- (void)didUpdateToObject:(User *)object {
    self.user = object;
}

@end
