import data from "std.tf"
alias data.map as map

library nested_throw_spec {

  nested_catch_trace:
    let {
      caught:
        try

          try
            throw "throwing"
          catch error, trace
            let {
              caught: {
                :error error,
                :trace trace
              }
            }
            throw caught

        catch cause, trace
          {
            :cause cause,
            :trace trace
          }

    }
    caught[:cause] == {
      :error "throwing"
      :trace [
               "fixtures/tweakflow/evaluation/throwing/try_catch_trace_nested.tf:12:13", # throw
               "fixtures/tweakflow/evaluation/throwing/try_catch_trace_nested.tf:7:5",   # let
               "fixtures/tweakflow/evaluation/throwing/try_catch_trace_nested.tf:6:3",   # nested_catch_trace
               "fixtures/tweakflow/evaluation/throwing/try_catch_trace_nested.tf:4:1",   # library
               "fixtures/tweakflow/evaluation/throwing/try_catch_trace_nested.tf:1:1"    # module
             ]
    }
    &&
    caught[:trace] ==
    [
      "fixtures/tweakflow/evaluation/throwing/try_catch_trace_nested.tf:20:13", # re-throw
      "fixtures/tweakflow/evaluation/throwing/try_catch_trace_nested.tf:14:13", # let
      "fixtures/tweakflow/evaluation/throwing/try_catch_trace_nested.tf:14:13", # catch
      "fixtures/tweakflow/evaluation/throwing/try_catch_trace_nested.tf:7:5",   # let
      "fixtures/tweakflow/evaluation/throwing/try_catch_trace_nested.tf:6:3",   # nested_catch_trace
      "fixtures/tweakflow/evaluation/throwing/try_catch_trace_nested.tf:4:1",   # library
      "fixtures/tweakflow/evaluation/throwing/try_catch_trace_nested.tf:1:1"    # module
    ]
}
