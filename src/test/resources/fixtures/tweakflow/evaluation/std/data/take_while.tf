import data from "std";
import expect, expect_error, to from "std/assert.tf";

alias data.take_while as take_while;


library take_while_spec {

  take_everything_from_some:
    expect(take_while((_) -> true, [1, 2, 3]), to.be([1, 2, 3]));

  take_nothing_from_some:
    expect(take_while((_) -> false, [1, 2, 3]), to.be([]));

  take_one_from_some:
    expect(take_while((x) -> x <= 1, [1, 2, 3]), to.be([1]));

  take_some_from_some:
    expect(take_while((x) -> x <= 2, [1, 2, 3]), to.be([1, 2]));

  take_some_from_some_with_cast:
    expect(take_while((x) -> if x <= 2 then "yay" else "", [1, 2, 3]), to.be([1, 2]));

  take_indexed_one_from_some:
    expect(take_while((_, i) -> i <= 0, [1, 2, 3]), to.be([1]));

  take_indexed_some_from_some:
    expect(take_while((_, i) -> i <= 1, [1, 2, 3]), to.be([1, 2]));

  take_indexed_some_from_some_with_cast:
    expect(take_while((_, i) -> if i <= 1 then "yay" else "", [1, 2, 3]), to.be([1, 2]));

  of_default:
    expect(take_while(nil, nil), to.be_nil());

  from_nil:
    expect(take_while((_) -> true, nil), to.be_nil());

  nil_predicate:
    expect_error(
      () -> take_while(nil, ["foo"]),
      to.have_code("NIL_ERROR")
    );

  bad_predicate:
    expect_error(
      () -> take_while(() -> true, ["foo"]),
      to.have_code("ILLEGAL_ARGUMENT")
    );
}