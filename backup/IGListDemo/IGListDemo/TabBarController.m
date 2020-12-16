//
//  TabBarController.m
//  IGListDemo
//
//  Created by Oleg Adamov on 08.11.2019.
//  Copyright © 2019 Odnoklssniki Ltd. All rights reserved.
//

#import "TabBarController.h"
#import "ViewController.h"
#import "SecondController.h"

@interface TabBarController ()

@end

@implementation TabBarController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    ViewController *vc = [ViewController new];
    UINavigationController *nav1 = [[UINavigationController alloc] initWithRootViewController:vc];
    vc.tabBarItem = [[UITabBarItem alloc] initWithTitle:nil image:[UIImage systemImageNamed:@"1.square.fill"] selectedImage:nil];
    
    SecondController *vc2 = [SecondController new];
    UINavigationController *nav2 = [[UINavigationController alloc] initWithRootViewController:vc2];
    vc2.tabBarItem = [[UITabBarItem alloc] initWithTitle:nil image:[UIImage systemImageNamed:@"2.square.fill"] selectedImage:nil];
    
    [self setViewControllers:@[nav1, nav2]];
}

@end
