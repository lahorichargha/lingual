/*
 * Copyright (c) 2007-2013 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.cascading.org/
 *
 * This file is part of the Cascading project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cascading.lingual.optiq;

import org.eigenbase.rel.*;
import org.eigenbase.rel.convert.ConverterRel;
import org.eigenbase.rel.convert.ConverterRule;
import org.eigenbase.relopt.*;

/**
 * Rule that converts a VALUES relational expression to Cascading convention.
 */
public class CascadingValuesRule extends RelOptRule
  {
  public static final CascadingValuesRule INSTANCE = new CascadingValuesRule();

  private CascadingValuesRule()
    {
    super(
        new RelOptRuleOperand(
            ConverterRel.class,
            Cascading.CONVENTION,
            new RelOptRuleOperand(
            ValuesRel.class, Convention.NONE)),
        "CascadingValuesRule" );
    }

      @Override
      public void onMatch(RelOptRuleCall call) {
          final RelNode converter = call.getRels()[0];
          final ValuesRel values = (ValuesRel) call.getRels()[1];

    RelTraitSet newTraits = values.getTraitSet().plus( Cascading.CONVENTION );

          call.transformTo(
    new CascadingValuesRel( values.getCluster(), newTraits, values.getRowType(), values.getTuples() ));
    }
  }