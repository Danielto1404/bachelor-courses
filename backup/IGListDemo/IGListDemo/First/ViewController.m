//
//  ViewController.m
//  IGListDemo
//
//  Created by Oleg Adamov on 08.11.2019.
//  Copyright © 2019 Odnoklssniki Ltd. All rights reserved.
//

// https://www.raywenderlich.com/9106-iglistkit-tutorial-better-uicollectionviews

#import "ViewController.h"
#import <IGListKit/IGListKit.h>
#import "User.h"
#import "SectionController.h"


@interface ViewController () <IGListAdapterDataSource>

@property (nonatomic) UICollectionView *collectionView;
@property (nonatomic) NSArray <User *> *users;
@property (nonatomic) IGListAdapter *adapter;

@end



@implementation ViewController

- (void)viewDidLoad {
    NSString *path = @"ok1246461440://open?search=oklive%20bands";
    NSRange range = [path rangeOfString:@"bands"];
    if (range.location != NSNotFound) {
        NSString *search = [path substringFromIndex:range.location + range.length];
        search = [search stringByRemovingPercentEncoding];
        NSLog(@"!! %@", search);
    } else {
        NSLog(@"!! not found range");
    }
    
    
    
    [super viewDidLoad];
    self.view.backgroundColor = UIColor.systemBackgroundColor;
    
    self.users = [User generateUsers];
    
    self.collectionView = [[UICollectionView alloc] initWithFrame:self.view.bounds collectionViewLayout:self.layout];
    self.collectionView.backgroundColor = UIColor.systemGroupedBackgroundColor;
    [self.view addSubview:self.collectionView];
    
    self.adapter = [[IGListAdapter alloc] initWithUpdater:[IGListAdapterUpdater new] viewController:self workingRangeSize:0];
    self.adapter.collectionView = self.collectionView;
    self.adapter.dataSource = self;
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"Add" style:UIBarButtonItemStylePlain target:self action:@selector(addUser)];
}

- (void)addUser {
    User *user = [[User alloc] initName:@"User 5" age:self.users.count * 10];
    NSMutableArray *temp = [[NSMutableArray alloc] initWithArray:self.users];
    [temp addObject:user];
    self.users = temp.copy;
    
    [self.adapter performUpdatesAnimated:YES completion:^(BOOL finished) {
        NSLog(@"!! updated");
    }];
}

- (NSArray<id<IGListDiffable>> *)objectsForListAdapter:(IGListAdapter *)listAdapter {
    return self.users;
}

- (IGListSectionController *)listAdapter:(IGListAdapter *)listAdapter sectionControllerForObject:(id)object {
    return [SectionController new];
}

- (UIView *)emptyViewForListAdapter:(IGListAdapter *)listAdapter {
    return nil;
}


- (void)viewDidLayoutSubviews {
    [super viewDidLayoutSubviews];
    self.collectionView.frame = self.view.bounds;
}


- (UICollectionViewFlowLayout *)layout {
    UICollectionViewFlowLayout *layout = [[UICollectionViewFlowLayout alloc] init];
    layout.itemSize = CGSizeMake(self.view.bounds.size.width, 60.f);
    layout.minimumInteritemSpacing = 6.f;
    layout.sectionInset = UIEdgeInsetsMake(12.f, 0.f, 12.f, 0.f);
    return layout;
}

@end
