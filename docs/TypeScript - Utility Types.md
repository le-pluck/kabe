# TypeScript - Utility Types

官方文档：[TypeScript: Documentation - Utility Types](https://www.typescriptlang.org/docs/handbook/utility-types.html) （*总的来说，看本文不如看官方文档（官方文档结合源码一起看）。*）

TypeScript 提供了几种实用**工具类型**（utility types）来促进公共类型（common type）转换。这些工具是全局可用的。

+ <a href="#awaited<type>"> `Awaited<Type>` </a>
+ <a href="#partial<type>"> `Partial<Type>` </a>
+ <a href="#required<type>"> `Required<Type>` </a>
+ <a href="#readonly<type>"> `Readonly<Type>` </a>
+ <a href="#record<keys,-type>"> `Record<Keys, Type>` </a>
+ <a href="#pick<type,-keys>"> `Pick<Type, Keys>` </a>
+ <a href="#omit<type,-keys>"> `Omit<Type, Keys>` </a>
+ <a href="#exclude<uniontype,-excludedmembers>"> `Exclude<UnionType, ExcludedMembers>` </a>
+ <a href="#extract<type,-union>"> `Extract<Type, Union>` </a>
+ <a href="#nonnullable<type>"> `NonNullable<Type>` </a>
+ <a href="#parameters<type>"> `Parameters<Type>` </a>
+ <a href="#constructorparameters<type>"> `ConstructorParameters<Type>` </a>
+ <a href="#returntype<type>"> `ReturnType<Type>` </a>
+ <a href="#instancetype<type>"> `InstanceType<Type>` </a>
+ <a href="#thisparametertype<type>"> `ThisParameterType<Type>` </a>
+ <a href="#omitthisparameter<type>"> `OmitThisParameter<Type>` </a>
+ <a href="#thistype<type>"> `ThisType<Type>` </a>
+ <a href="#intrinsic-string-manipulation-types"> `Intrinsic String Manipulation Types` </a>
+ <a href="#uppercase<stringtype>"> `Uppercase<StringType>` </a>
+ <a href="#lowercase<stringtype>"> `Lowercase<StringType>` </a>
+ <a href="#capitalize<stringtype>"> `Capitalize<StringType>` </a>
+ <a href="#uncapitalize<stringtype>"> `Uncapitalize<StringType>` </a>



## `Awaited<Type>`

## `Partial<Type>`

### Source Code

``` js
/**
 * Make all properties in T optional
 */
type Partial<T> = {
    [P in keyof T]?: T[P];
};
```

## `Required<Type>`

### Source Code

``` js
/**
 * Make all properties in T required
 */
type Required<T> = {
    [P in keyof T]-?: T[P];
};
```

## `Readonly<Type>`

## `Record<Keys, Type>`

## `Pick<Type, Keys>`

### Source Code

``` js
/**
 * From T, pick a set of properties whose keys are in the union K
 */
type Pick<T, K extends keyof T> = {
    [P in K]: T[P];
};
```

## `Omit<Type, Keys>`

省略/剔除

### Source Code

``` js
/**
 * Construct a type with the properties of T except for those in type K.
 */
type Omit<T, K extends keyof any> = Pick<T, Exclude<keyof T, K>>;
```

## `Exclude<UnionType, ExcludedMembers>`

排除/不包括

与 `Extract` 相反。

### Source Code

``` js
/**
 * Exclude from T those types that are assignable to U
 */
type Exclude<T, U> = T extends U ? never : T;
```

## `Extract<Type, Union>`

提取/包括

与 `Exclude` 相反。

### Source Code

``` js
/**
 * Extract from T those types that are assignable to U
 */
type Extract<T, U> = T extends U ? T : never;
```

## `NonNullable<Type>`

## `Parameters<Type>`

## `ConstructorParameters<Type>`

## `ReturnType<Type>`

## `InstanceType<Type>`

## `ThisParameterType<Type>`

## `OmitThisParameter<Type>`

## `ThisType<Type>`

## `Intrinsic String Manipulation Types`

## `Uppercase<StringType>`

## `Lowercase<StringType>`

## `Capitalize<StringType>`

## `Uncapitalize<StringType>`


1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1
1