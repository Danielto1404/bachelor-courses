//
//  User.h
//  IGListDemo
//
//  Created by Oleg Adamov on 08.11.2019.
//  Copyright © 2019 Odnoklssniki Ltd. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "IGListDiffable.h"
#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface User : NSObject <IGListDiffable>

@property (nonatomic, readonly) NSString *name;
@property (nonatomic, readonly) NSUInteger age;
@property (nonatomic) CGFloat calculatedHeight;

- (instancetype)initName:(NSString *)name age:(NSUInteger)age;

+ (NSArray <User *> *)generateUsers;

@end


@interface Master: NSObject <IGListDiffable>

- (instancetype)initWithUser:(NSArray<User *> *)users;
- (void)addUser:(User *)user;

- (NSInteger)usersCount;
- (User *)userAtIndex:(NSInteger)index;

@end

NS_ASSUME_NONNULL_END
