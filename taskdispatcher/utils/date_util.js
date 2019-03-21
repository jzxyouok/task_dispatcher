/**
 \* Created with IntelliJ IDEA.
 \* @author: 彭诗杰
 \* @date: 2019/1/9
 \* @time: 12:23
 \* Description:
 \*/
let SIGN_REGEXP = /([yMdhsm])(\1*)/g;
let DEFAULT_PATTERN = 'yyyy-MM-dd';
let YYYYMMDDHHMMSS = 'yyyy-MM-dd hh:mm:ss';

export default {
  dateFormat: format,
  dateParse: parse,
  YYYYMMDDHHMMSS: YYYYMMDDHHMMSS
}

function padding(s, len) {
  let l = len - (s + '').length;
  for (let i = 0; i < l; i++) {
    s = '0' + s;
  }
  return s;
}

function format(date, pattern) {
  pattern = pattern || DEFAULT_PATTERN;
  return pattern.replace(SIGN_REGEXP, function ($0) {
    switch ($0.charAt(0)) {
      case 'y':
        return padding(date.getFullYear(), $0.length);
      case 'M':
        return padding(date.getMonth() + 1, $0.length);
      case 'd':
        return padding(date.getDate(), $0.length);
      case 'w':
        return date.getDay() + 1;
      case 'h':
        return padding(date.getHours(), $0.length);
      case 'm':
        return padding(date.getMinutes(), $0.length);
      case 's':
        return padding(date.getSeconds(), $0.length)
    }
  })
}

function parse(dateString, pattern) {
  let matchs1 = pattern.match(SIGN_REGEXP);
  let matchs2 = dateString.match(/(\d)+/g);
  if (matchs1.length === matchs2.length) {
    let _date = new Date(1970, 0, 1);
    for (let i = 0; i < matchs1.length; i++) {
      let _int = parseInt(matchs2[i]);
      let sign = matchs1[i];
      switch (sign.charAt(0)) {
        case 'y':
          _date.setFullYear(_int);
          break;
        case 'M':
          _date.setMonth(_int - 1);
          break;
        case 'd':
          _date.setDate(_int);
          break;
        case 'h':
          _date.setHours(_int);
          break;
        case 'm':
          _date.setMinutes(_int);
          break;
        case 's':
          _date.setSeconds(_int);
          break;
      }
    }
    return _date
  }
  return null
}
