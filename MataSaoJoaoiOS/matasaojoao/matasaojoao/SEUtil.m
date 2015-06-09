//
//  SEUtil.m
//  semut
//
//  Created by Julio Rocha on 25/11/13.
//  Copyright (c) 2013 Login. All rights reserved.
//
#import <CommonCrypto/CommonDigest.h>
#import "SEUtil.h"
#define kNome @"nome"
#define kEmail @"email"
#define kTelefone @"telefone"
#define kSenha @"senha"
@implementation NSString(MD5)

- (NSString*)MD5
{
    // Create pointer to the string as UTF8
    const char *ptr = [self UTF8String];
    
    // Create byte array of unsigned chars
    unsigned char md5Buffer[CC_MD5_DIGEST_LENGTH];
    
    // Create 16 byte MD5 hash value, store in buffer
    CC_MD5(ptr, strlen(ptr), md5Buffer);
    
    // Convert MD5 value in the buffer to NSString of hex values
    NSMutableString *output = [NSMutableString stringWithCapacity:CC_MD5_DIGEST_LENGTH * 2];
    for(int i = 0; i < CC_MD5_DIGEST_LENGTH; i++)
        [output appendFormat:@"%02x",md5Buffer[i]];
    
    return output;
}

@end

@implementation UIColor(HexaString)

+(UIColor *) colorFromHexString:(NSString *) cor {
    
    unsigned result=0;
    NSScanner *scanner = [NSScanner scannerWithString:[cor stringByReplacingOccurrencesOfString:@"#" withString:@""]];
    
    [scanner scanHexInt:&result];
    
    return [UIColor colorWithRed:((float)((result & 0xFF0000) >> 16))/255.0 green:((float)((result & 0xFF00) >> 8))/255.0 blue:((float)(result & 0xFF))/255.0 alpha:1.0];
    
}

+(UIColor *) sucomColor {
    
    return [self colorFromHexString:kCorSucom];
    
}

+(UIColor *) transalvadorColor {
    
    return [self colorFromHexString:kCorTransalvador];
    
}

@end

@implementation SEUtil

+(SEUsuario *) getUser {
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    
    NSDictionary *dictionary =[defaults objectForKey:@"usuario"];
    
    if(dictionary) {
    
        return [[SEUsuario alloc]initWithDictionary:dictionary];
        
    }
    
    return nil;

}

+ (void) setDefault:(id)dic forKey:(NSString *) key {
        NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
        [defaults setObject:dic forKey:key];
    
}

+ (void) setBool:(BOOL)param forKey:(NSString *) key {
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setBool:param forKey:key];
    
}

+ (BOOL) boolForKey:(NSString *) key {
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
   return [defaults boolForKey:key];
    
}

+(id) defaultForKey:(NSString *)key {
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    return [defaults objectForKey:key];
}

+(void) saveUser:(NSDictionary *) user {
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];

    [defaults setObject:user forKey:@"usuario"];
    
}

+(void) clearUser {
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    
    [defaults removeObjectForKey:@"usuario"];
}

+(NSString *) servletKey {
    
    NSString *chave = kChaveServlet;
    NSDateFormatter *formatter = [[NSDateFormatter alloc]init];
    [formatter setDateFormat:@"yyyyMMdd"];
    chave = [chave stringByAppendingString:[formatter stringFromDate:[[NSDate alloc]init]]];
    chave = [chave MD5];
    return chave;
    
}

+(BOOL) isEmailValid:(NSString *) email {
  
    NSString *regEx = @".+@.+\\.[a-z]+";
    NSRange range = [email rangeOfString:regEx options:NSRegularExpressionSearch];
    return range.location != NSNotFound;
    
}

@end
