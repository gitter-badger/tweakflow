
library lib {
  f: (x) -> x;
}

library operator_spec {

  nil_void: (nil is void)   == true;

  bool: (true is boolean)   == true;
  l0: (0 is long)           == true;
  d0: (0.0 is double)       == true;
  f: (lib.f is function)    == true;
  m: ({} is dict)           == true;
  a: ([] is list)           == true;
  s: ("" is string)         == true;
  dt: (1970-01-01T00:00:00Z is datetime) == true;

  nnil: (!(nil is long))     == true;
  nl0:  (!(0 is double))     == true;
  nd0:  (!(0.0 is long))     == true;
  nf:  (!(lib.f is long))    == true;
  nm:  (!({} is long))       == true;
  na:  (!([] is long))       == true;
  ns: (!("" is long))        == true;
  ndt: (!(1970-01-01T00:00:00Z is long)) == true;

}