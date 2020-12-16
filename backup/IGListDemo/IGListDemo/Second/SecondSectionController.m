//
//  SecondSectionController.m
//  IGListDemo
//
//  Created by Oleg Adamov on 11.11.2019.
//  Copyright © 2019 Odnoklssniki Ltd. All rights reserved.
//

#import "SecondSectionController.h"
#import "User.h"
#import "CollectionViewCell.h"

@interface SecondSectionController ()

@property (nonatomic) Master *master;

@end

@implementation SecondSectionController

- (instancetype)init {
    self = [super init];
    self.inset = UIEdgeInsetsMake(4.f, 0.f, 8.f, 0.f);
    return self;
}

- (CGSize)sizeForItemAtIndex:(NSInteger)index {
    return CGSizeMake(self.collectionContext.containerSize.width, 60.f);
}

- (NSInteger)numberOfItems {
    return self.master.usersCount;
}

- (UICollectionViewCell *)cellForItemAtIndex:(NSInteger)index {
    CollectionViewCell *cell = [self.collectionContext dequeueReusableCellOfClass:CollectionViewCell.class forSectionController:self atIndex:index];
    [cell updateWithUser:[self.master userAtIndex:index]];
    return cell;
}

- (void)didUpdateToObject:(Master *)object {
    self.master = object;
}

@end
