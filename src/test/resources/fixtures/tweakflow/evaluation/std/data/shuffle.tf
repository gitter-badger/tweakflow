import data from "std";
import expect, expect_error, to from "std/assert.tf";

alias data.shuffle as shuffle;

library shuffle_spec {

  empty_list:
    expect(shuffle([], 123), to.be([]));

  nil_shuffle:
    expect(shuffle([1,2,3]), to.be([1,3,2]));

  foo_shuffle:
    expect(shuffle([1,2,3,4,5], "foo"), to.be([2, 4, 3, 5, 1]));

  foo_shuffle_alt:
    expect(shuffle([:a, :b, :c, :d, :e], "foo"), to.be([:b, :d, :c, :e, :a]));

  bar_shuffle:
    expect(shuffle([1,2,3,4,5], "bar"), to.be([2, 3, 1, 5, 4]));

  bar_shuffle_alt:
    expect(shuffle([:a, :b, :c, :d, :e], "bar"), to.be([:b, :c, :a, :e, :d]));

  of_nil:
    expect(shuffle(nil), to.be_nil());

}