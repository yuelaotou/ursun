wee={};
wee.encoder = function() {

	defaultEncoder = 'base64';
	// ------------------------------------------------------------------------BASE64----------------------------------------------------------------------//
	BASE64 = {
		base64EncodeChars : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",

		base64DecodeChars : new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61,
				-1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1,
				-1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36,
				37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1,
				-1, -1, -1, -1)
	}
	BASE64.base64encode = function(str) {
		var out, i, len;
		var c1, c2, c3;
		len = str.length;
		i = 0;
		out = "";
		while (i < len) {
			c1 = str.charCodeAt(i++) & 0xff;
			if (i == len) {
				out += this.base64EncodeChars.charAt(c1 >> 2);
				out += this.base64EncodeChars.charAt((c1 & 0x3) << 4);
				out += "==";
				break;
			}
			c2 = str.charCodeAt(i++);
			if (i == len) {
				out += this.base64EncodeChars.charAt(c1 >> 2);
				out += this.base64EncodeChars.charAt(((c1 & 0x3) << 4)
						| ((c2 & 0xF0) >> 4));
				out += this.base64EncodeChars.charAt((c2 & 0xF) << 2);
				out += "=";
				break;
			}
			c3 = str.charCodeAt(i++);
			out += this.base64EncodeChars.charAt(c1 >> 2);
			out += this.base64EncodeChars.charAt(((c1 & 0x3) << 4)
					| ((c2 & 0xF0) >> 4));
			out += this.base64EncodeChars.charAt(((c2 & 0xF) << 2)
					| ((c3 & 0xC0) >> 6));
			out += this.base64EncodeChars.charAt(c3 & 0x3F);
		}
		return out;
	}
	BASE64.base64decode = function(str) {
		var c1, c2, c3, c4;
		var i, len, out;
		len = str.length;
		i = 0;
		out = "";
		while (i < len) {
			/* c1 */
			do {
				c1 = this.base64DecodeChars[str.charCodeAt(i++) & 0xff];
			} while (i < len && c1 == -1);
			if (c1 == -1)
				break;
			/* c2 */
			do {
				c2 = this.base64DecodeChars[str.charCodeAt(i++) & 0xff];
			} while (i < len && c2 == -1);
			if (c2 == -1)
				break;
			out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
			/* c3 */
			do {
				c3 = str.charCodeAt(i++) & 0xff;
				if (c3 == 61)
					return out;
				c3 = this.base64DecodeChars[c3];
			} while (i < len && c3 == -1);
			if (c3 == -1)
				break;
			out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
			/* c4 */
			do {
				c4 = str.charCodeAt(i++) & 0xff;
				if (c4 == 61)
					return out;
				c4 = this.base64DecodeChars[c4];
			} while (i < len && c4 == -1);
			if (c4 == -1)
				break;
			out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
		}
		return out;
	}
	BASE64.utf16to8 = function(str) {
		var out, i, len, c;
		out = "";
		len = str.length;
		for (i = 0; i < len; i++) {
			c = str.charCodeAt(i);
			if ((c >= 0x0001) && (c <= 0x007F)) {
				out += str.charAt(i);
			} else if (c > 0x07FF) {
				out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
				out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
				out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
			} else {
				out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
				out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
			}
		}
		return out;
	}
	BASE64.utf8to16 = function(str) {
		var out, i, len, c;
		var char2, char3;
		out = "";
		len = str.length;
		i = 0;
		while (i < len) {
			c = str.charCodeAt(i++);
			switch (c >> 4) {
				case 0 :
				case 1 :
				case 2 :
				case 3 :
				case 4 :
				case 5 :
				case 6 :
				case 7 :
					// 0xxxxxxx
					out += str.charAt(i - 1);
					break;
				case 12 :
				case 13 :
					// 110x xxxx 10xx xxxx
					char2 = str.charCodeAt(i++);
					out += String.fromCharCode(((c & 0x1F) << 6)
							| (char2 & 0x3F));
					break;
				case 14 :
					// 1110 xxxx 10xx xxxx 10xx xxxx
					char2 = str.charCodeAt(i++);
					char3 = str.charCodeAt(i++);
					out += String.fromCharCode(((c & 0x0F) << 12)
							| ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
					break;
			}
		}
		return out;
	}
	BASE64.encode = function(str) {
		return this.base64encode(this.utf16to8(str));
	}
	BASE64.decode = function(str) {
		return this.utf8to16(this.base64decode(str));
	}
	// ------------------------------------------------------------------------DES----------------------------------------------------------------------//
	var DES = {
		KEY : "47944980",
		// initial permutation IP
		IP_Table : [58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12,
				4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16,
				8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3,
				61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7],
		// final permutation IP^-1
		IPR_Table : [40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63,
				31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61,
				29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59,
				27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25],
		// permuted choice table (key)
		PC1_Table : [57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10,
				2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47,
				39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45,
				37, 29, 21, 13, 5, 28, 20, 12, 4],
		// permuted choice key (table)
		PC2_Table : [14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4,
				26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40,
				51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32],
		// number left rotations of pc1
		LOOP_Table : [1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1],
		// expansion operation matrix
		E_Table : [32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13,
				12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23,
				24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1],
		// 32-bit permutation function P used on the output of the S-boxes
		P_Table : [16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10,
				2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25],

		// The (in)famous S-boxes
		S_Box : [
				// S1
				[[14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7],
						[0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8],
						[4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0],
						[15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13]],
				// S2
				[[15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10],
						[3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5],
						[0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15],
						[13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9]],
				// S3
				[[10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8],
						[13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1],
						[13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7],
						[1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12]],
				// S4
				[[7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15],
						[13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9],
						[10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4],
						[3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14]],
				// S5
				[[2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9],
						[14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6],
						[4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14],
						[11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3]],
				// S6
				[[12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11],
						[10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8],
						[9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6],
						[4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13]],
				// S7
				[[4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1],
						[13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6],
						[1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2],
						[6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12]],
				// S8
				[[13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7],
						[1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2],
						[7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8],
						[2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11]]],
		Oct2Bin : ["0000", "0001", "0010", "0011", "0100", "0101", "0110",
				"0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110",
				"1111"],
		// str 为八位的字符
		subKeys : new Array(16),
		key : "",
		text : ""
	};

	DES.init = function(key, text) {
		if (key != this.key) {
			this.key = key;
			this.GenSubKey();
		}
		this.text = text
				+ "        ".substring(0, parseInt("07654321"
										.charAt(text.length % 8), 10));
	};

	DES.GenSubKey = function() {
		var arr = this.Permute(this.Byte2Bit(this.key), this.PC1_Table);
		var AL = arr.slice(0, 28);
		var AR = arr.slice(28, 56);
		for (var i = 0; i < 16; i++) {
			for (var j = 0, k = this.LOOP_Table[i]; j < k; j++) {
				AL.push(AL.shift());
				AR.push(AR.shift());
			}
			this.subKeys[i] = this.Permute(AL.concat(AR), this.PC2_Table);
		}
	};
	// 数组ar存储二进制数据,该函数将从头开始的每八位转换成对应的字符
	DES.Bit2Byte = function(ar, fCh) {
		var str = "";
		if (fCh == "byte") {
			var tmpAr = ar.join("").match(/.{8}/g);
			for (var i = 0, j = tmpAr.length; i < j; i++)
				str += String.fromCharCode(parseInt(tmpAr[i], 2));
		} else if (fCh == "hex") {
			var tmpAr = ar.join("").match(/.{4}/g);
			for (var i = 0, j = tmpAr.length; i < j; i++)
				str += "0123456789abcdef".charAt(parseInt(tmpAr[i], 2));
		} else
			return "Error:Second param is wrong.";
		return str;
	};
	// 将字符串转换成二进制数组
	DES.Byte2Bit = function(str) {
		for (var i = 0, j = 8 * str.length, ar = [], ch = ""; i < j; i++) {
			var k = 7 - i % 8;
			if (k == 7)
				ch = str.charCodeAt(parseInt(i / 8, 10));
			ar.push((ch >> k) & 1);
		}
		return ar;
	};

	// 将16进制字符串转变成二进制数组
	DES.Hex2Bin = function(str) {
		for (var i = 0, j = str.length, s = ""; i < j; i++)
			s += this.Oct2Bin[parseInt(str.charAt(i), 16)];
		return s.split("");
	};

	DES.Xor = function(A, B) {
		for (var i = 0, j = B.length, rtn = new Array(j); i < j; i++)
			rtn[i] = A[i] ^ B[i];
		return rtn;
	}

	DES.Permute = function(ar, tb) {
		for (var i = 0, j = tb.length, rtn = new Array(j); i < j; i++)
			rtn[i] = ar[tb[i] - 1];
		return rtn;
	};

	DES.F_func = function(Ri, Ki) {
		return this.Permute(this.S_func(this.Xor(
						this.Permute(Ri, this.E_Table), Ki)), this.P_Table);
	};
	// ar为输入48位串数组
	DES.S_func = function(ar) {
		for (var i = 0, arRtn = []; i < 8; i++) {
			var x = i * 6;
			var j = parseInt("" + ar[x] + ar[x + 5], 2);
			var k = parseInt(ar.slice(x + 1, x + 5).join(""), 2);
			arRtn = arRtn.concat(this.Oct2Bin[this.S_Box[i][j][k]].split(""));
		}
		return arRtn;
	}

	// mode参数为处理模式."Encrypt":加密 "Decrypt":解密,默认为加密
	DES.Encrypt = function(mode) {
		mode = mode ? mode : "Encrypt";
		if (mode == "Decrypt")
			var plainTextAr = this.Hex2Bin(this.text).join("").match(/.{64}/g);
		else
			var plainTextAr = this.Byte2Bit(this.text).join("").match(/.{64}/g);
		for (var i = 0, j = plainTextAr.length; i < j; i++) {
			var arr = this.Permute(plainTextAr[i].split(""), this.IP_Table)
			var AL = arr.slice(0, 32);
			var AR = arr.slice(32, 64);
			if (mode == "Decrypt") {
				for (var k = 15; k > -1; k--) {
					var tmp = AR.slice(0, 32);
					AR = this.Xor(this.F_func(AR, this.subKeys[k]), AL);
					AL = tmp;
				}
			} else {
				for (var k = 0; k < 16; k++) {
					var tmp = AR.slice(0, 32);
					AR = this.Xor(this.F_func(AR, this.subKeys[k]), AL);
					AL = tmp;
				}
			}
			plainTextAr[i] = this.Bit2Byte(this.Permute(AR.concat(AL),
							this.IPR_Table), (mode == "Decrypt"
							? "byte"
							: "hex"));
		}
		return plainTextAr.join("").trim();
	}

	DES.encode = function(source, key) {
		this.init(DES.KEY, source);
		return DES.Encrypt("Encrypt");
	}

	DES.decode = function(source, key) {
		DES.init(DES.KEY, source);
		return DES.Encrypt("Decrypt");
	}

	return {
		encode : function(source, type) {
			return eval((type ? type : defaultEncoder).toUpperCase()
					+ ".encode('" + source + "')");
		},
		decode : function(source, type) {
			return eval((type ? type : defaultEncoder).toUpperCase()
					+ ".decode('" + source + "')");
		}
	};
}();