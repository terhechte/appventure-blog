//: Playground - noun: a place where people can play

import Cocoa

var str = "Hello, playground"

enum A {
    enum AB {
        case aa
        case bb
        case cc
    }
    case a
    case b
    case c

}


let ax: String? = "test"
let bx: Any = ax

let up = Mirror(reflecting: ax)
let ab = ["a", "b", 0, "c"]


typealias Config = (RAM: Int, CPU: String, GPU: String)

let ppp = (0, "", "") as Config

let ya: Config = (RAM: 4, CPU: "", GPU: "")

enum FileNode {
    case File(name: String)
    case Folder(name: String, files: [FileNode])
}

let f = FileNode.Folder(name: "a", files: [.File(name: "x"), .File(name: "y"), .File(name: "z")])

//enum ABC: CGPoint {
//    
//}

//extension CGSize : StringLiteralConvertible {
//    public init(stringLiteral value: Self.StringLiteralType) {
//        self = CGSizeFromString(value)
//    }
//}

extension CGSize: StringLiteralConvertible {
    public init(stringLiteral value: String) {
        let size = NSSizeFromString(value)
        self.init(width: size.width, height: size.height)
    }
    
    public init(extendedGraphemeClusterLiteral value: String) {
        let size = NSSizeFromString(value)
        self.init(width: size.width, height: size.height)
    }
    
    public init(unicodeScalarLiteral value: String) {
        let size = NSSizeFromString(value)
        self.init(width: size.width, height: size.height)
    }
}


enum Device: CGSize {
   case iPhone3GS = "0, 0"
   case iPhone5 = "1, 1"
}

func ==(lhs: Trade, rhs: Trade) -> Bool {
    switch (lhs, rhs) {
    case let (.Buy(stock1, amount1), .Buy(stock2, amount2))
        where stock1 == stock2 && amount1 == amount2:
        return true
        default: return false
    }
}

enum Trade {
    case Buy(stock: String, amount: Int)
    case Sell(stock: String, amount: Int)
}

@objc class OTrade: NSObject {
    var type: Int
    var stock: String
    var amount: Int
    init(type: Int, stock: String, amount: Int) {
        self.type = type
        self.stock = stock
        self.amount = amount
    }
}

extension Trade  {
    
    func toObjc() -> OTrade {
        switch self {
        case let .Buy(stock, amount):
            return OTrade(type: 0, stock: stock, amount: amount)
        case let .Sell(stock, amount):
            return OTrade(type: 1, stock: stock, amount: amount)
        }
    }
    
    static func fromObjc(source: OTrade) -> Trade? {
        switch (source.type) {
        case 0: return Trade.Buy(stock: source.stock, amount: source.amount)
        case 1: return Trade.Sell(stock: source.stock, amount: source.amount)
        default: return nil
        }
    }
}


