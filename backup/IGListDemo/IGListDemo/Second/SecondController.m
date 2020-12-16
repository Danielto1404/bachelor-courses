//
//  SecondController.m
//  IGListDemo
//
//  Created by Oleg Adamov on 11.11.2019.
//  Copyright © 2019 Odnoklssniki Ltd. All rights reserved.
//

#import "SecondController.h"
#import "IGListKit.h"
#import "User.h"
#import "SecondSectionController.h"

@interface SecondController () <IGListAdapterDataSource>

@property (nonatomic) UICollectionView *collectionView;
@property (nonatomic) Master *master;
@property (nonatomic) IGListAdapter *adapter;

@end


@implementation SecondController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.view.backgroundColor = UIColor.systemBackgroundColor;
    
    self.master = [[Master alloc] initWithUser:[User generateUsers]];
    
    self.collectionView = [[UICollectionView alloc] initWithFrame:self.view.bounds collectionViewLayout:self.layout];
    self.collectionView.backgroundColor = UIColor.systemGroupedBackgroundColor;
    [self.view addSubview:self.collectionView];
    
    self.adapter = [[IGListAdapter alloc] initWithUpdater:[IGListAdapterUpdater new] viewController:self workingRangeSize:0];
    self.adapter.collectionView = self.collectionView;
    self.adapter.dataSource = self;
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"Add" style:UIBarButtonItemStylePlain target:self action:@selector(addUser)];
}

- (void)addUser {
    User *user = [[User alloc] initName:@"User 5" age:self.master.usersCount * 10];
    [self.master addUser:user];
    
    // нельзя, потому что кол-во элементов изменилось, а Adapter для апдейта не подготовит изменений
    [self.adapter performUpdatesAnimated:YES completion:^(BOOL finished) {
        NSLog(@"!! updated");
    }];
    
//    [self.adapter reloadDataWithCompletion:^(BOOL finished) {
//        NSLog(@"!! reloaded");
//    }];
}

- (NSArray<id<IGListDiffable>> *)objectsForListAdapter:(IGListAdapter *)listAdapter {
    return @[self.master];
}

- (IGListSectionController *)listAdapter:(IGListAdapter *)listAdapter sectionControllerForObject:(id)object {
    return [SecondSectionController new];
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
