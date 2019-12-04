#import "CDVNativeLogs.h"
#import <Cordova/CDV.h>

@implementation CDVNativeLogs


- (void)pluginInitialize
{
    NSString* pathForLog = [self getPath];
    [[NSFileManager defaultManager] removeItemAtPath:pathForLog error:nil];
    freopen([pathForLog cStringUsingEncoding:NSASCIIStringEncoding],"a+",stderr);
}

- (NSString*) getPath {
    NSArray *allPaths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [allPaths objectAtIndex:0];
    NSString *pathForLog = [documentsDirectory stringByAppendingPathComponent:@"cordova-plugin-nativelogs.txt"];
    return pathForLog;
}

- (void)getLog:(CDVInvokedUrlCommand*)command {

    
    NSString* callbackId = command.callbackId;
    if (command.arguments.count != 1)
    {
        NSString* error = @"missing arguments in getLog";
        NSLog(@"CDVNativeLogs: %@",error);
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
        return ;
    }
    
    int nbLines = 1000;  // maxline
    BOOL bClipboard = false;
    id value = [command argumentAtIndex:0];
    if ([value isKindOfClass:[NSNumber class]]) {
        nbLines = [value intValue];
    }

    NSString *stringContent = [NSString stringWithContentsOfFile:pathForLog encoding:NSUTF8StringEncoding error:nil];

    NSArray *brokenByLines=[stringContent componentsSeparatedByString:@"\n"];

    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:brokenByLines];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}
  

@end
