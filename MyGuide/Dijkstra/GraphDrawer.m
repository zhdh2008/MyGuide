//
//  GraphDrawer.m
//  MyGuide
//
//  Created by Kamil Lelonek on 5/23/14.
//  Copyright (c) 2014 - Open Source (Apache 2.0 license). All rights reserved.
//

#import "GraphDrawer.h"
#import "AFParsedData.h"
#import "AFWay.h"
#import "Vertex.h"

@interface GraphDrawer ()

@property NSMutableArray *nodes;
@property AFParsedData   *data;
@property Graph          *graph;

@end

@implementation GraphDrawer

- (id) init
{
    self = [super self];
    if (self) {
        _nodes = [NSMutableArray new];
        _data  = [AFParsedData sharedParsedData];
        _graph = _data.graph;
        [self buildNodesArray];
    }
    return self;
}

- (void) buildNodesArray
{
    for (AFWay *way in self.data.waysArray) {
        for (AFNode *node in way.nodesArray) {
            [self.nodes addObject: node];
        }
    }
}

- (MKPolyline *) findShortestPathBetweenLocation: (CLLocation *)sourceLocation andLocation: (CLLocation *)destinationLocation
{
    AFNode  *sourceNode      = [self findClosestNodeForLocation: sourceLocation];
    AFNode  *destinationNode = [self findClosestNodeForLocation: destinationLocation];
    NSArray *shortestPath    = [self.graph findPathBetweenNode: sourceNode andNode: destinationNode];
    int     counter          = 0;

    CLLocationCoordinate2D coordinates[[shortestPath count]];

    for (Vertex *v in shortestPath) {
        AFNode *node = v.position;
        coordinates[counter++] = CLLocationCoordinate2DMake([node.latitude doubleValue], [node.longitude doubleValue]);
    }

    return [MKPolyline polylineWithCoordinates: coordinates count: [shortestPath count]];
}

- (AFNode *) findClosestNodeForLocation: (CLLocation *)location
{
    AFNode      *closestNode = [self.nodes firstObject];
    for (AFNode *node in self.nodes) {
        NSInteger closestDistance = [closestNode distanceFromLocation: location];
        NSInteger currentDistance = [node distanceFromLocation: location];
        if (currentDistance < closestDistance) {
            closestNode = node;
        }
    }
    return closestNode;
}

@end
