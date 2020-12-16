//
//  User.m
//  IGListDemo
//
//  Created by Oleg Adamov on 08.11.2019.
//  Copyright © 2019 Odnoklssniki Ltd. All rights reserved.
//

#import "User.h"

@implementation User

- (instancetype)initName:(NSString *)name age:(NSUInteger)age {
    self = [super init];
    _name = name;
    _age = age;
    return self;
}


+ (NSArray<User *> *)generateUsers {
    NSMutableArray *users = [NSMutableArray new];
    for (NSUInteger i = 1 ; i < 10 ; i++) {
        [users addObject:[[User alloc] initName:[NSString stringWithFormat:@"User %lu", (unsigned long)i] age:i * 10]];
    }
    
    return users.copy;
}

- (id<NSObject>)diffIdentifier {
    return self.name;
}

- (BOOL)isEqualToDiffableObject:(User *)object {
    return [self.name isEqualToString:object.name];
}

@end


@interface Master ()

@property (nonatomic) NSMutableArray<User *> *users;

@end


@implementation Master

- (instancetype)initWithUser:(NSArray<User *> *)users {
    self = [super init];
    _users = [[NSMutableArray alloc] initWithArray:users];
    return self;
}

- (void)addUser:(User *)user {
    [self.users addObject:user];
}

- (NSInteger)usersCount {
    return self.users.count;
}

- (User *)userAtIndex:(NSInteger)index {
    return self.users[index];
}

- (id<NSObject>)diffIdentifier {
    return self;
}

- (BOOL)isEqualToDiffableObject:(id<IGListDiffable>)object {
    return [self isEqual:object];
}

@end
