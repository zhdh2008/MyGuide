//
// Created by Kamil Lelonek on 5/14/14.
// Copyright (c) 2014 - Open Source (Apache 2.0 license). All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFNode.h"

@interface DijkstraAlgorithm : NSObject

- (NSArray *) findShortestBathBetween: (CLLocation *)sourceLocation and: (CLLocation *)destinationLocation;

@end