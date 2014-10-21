#!/usr/bin/perl

%nodes=(
    'IMPhi' => 'IMNode',
    'IMConstant' => 'IMNode',
    'IMReadArray' => 'IMNode',
    'IMReadLocalVariable' => 'IMNode',
    'IMStoreArray' => 'IMNode',
    'IMStoreLocalVariable' => 'IMNode',
    'IMArrayLength' => 'IMNode',
    'IMCall' => 'IMBranch',
    'IMCast' => 'IMUnaryNode',
    'IMStackOperationPOP' => 'IMNode',
    'IMStackOperationPOP2' => 'IMNode',
    'IMStackOperationDUP' => 'IMNode',
    'IMStackOperationDUP_X1' => 'IMNode',
    'IMStackOperationDUP_X2' => 'IMNode',
    'IMStackOperationDUP2' => 'IMNode',
    'IMStackOperationDUP2_X1' => 'IMNode',
    'IMStackOperationDUP2_X2' => 'IMNode',
    'IMStackOperationSWAP' => 'IMNode',
    'IMCastD2F' => 'IMCast',
    'IMCastD2I' => 'IMCast',
    'IMCastD2L' => 'IMCast',
    'IMCastF2D' => 'IMCast',
    'IMCastF2I' => 'IMCast',
    'IMCastF2L' => 'IMCast',
    'IMCastI2B' => 'IMCast',
    'IMCastI2C' => 'IMCast',
    'IMCastI2D' => 'IMCast',
    'IMCastI2F' => 'IMCast',
    'IMCastI2L' => 'IMCast',
    'IMCastI2S' => 'IMCast',
    'IMCastL2D' => 'IMCast',
    'IMCastL2F' => 'IMCast',
    'IMCastL2I' => 'IMCast',
    'IMCaughtException' => 'IMNode',
    'IMCheckCast' => 'IMUnaryNode',
    'IMAdd' => 'IMBinaryNode',
    'IMDiv' => 'IMBinaryNode',
    'IMCompare' => 'IMBinaryNode',
    'IMDGCompare' => 'IMCompare',
    'IMDLCompare' => 'IMCompare',
    'IMFGCompare' => 'IMCompare',
    'IMFLCompare' => 'IMCompare',
    'IMLCompare' => 'IMCompare',
    'IMMul' => 'IMBinaryNode',
    'IMNeg' => 'IMUnaryNode',
    'IMRem' => 'IMBinaryNode',
#    'IMReturn' => 'IMNode',
    'IMEpilog' => 'IMNode',
    'IMSub' => 'IMBinaryNode',
    'IMEQConditionalBranch' => 'IMConditionalBranch',
    'IMGEConditionalBranch' => 'IMConditionalBranch',
    'IMGTConditionalBranch' => 'IMConditionalBranch',
    'IMLEConditionalBranch' => 'IMConditionalBranch',
    'IMLTConditionalBranch' => 'IMConditionalBranch',
    'IMNEConditionalBranch' => 'IMConditionalBranch',
    'IMGetField' => 'IMNode',
    'IMGetStatic' => 'IMNode',
    'IMGoto' => 'IMBranch',
    'IMBitAnd' => 'IMBinaryNode',
    'IMBitOr' => 'IMBinaryNode',
    'IMBitXor' => 'IMBinaryNode',
    'IMShiftLeft' => 'IMBinaryNode',
    'IMShiftRight' => 'IMBinaryNode',
    'IMShiftRight2' => 'IMBinaryNode',
    'IMInstanceOf' => 'IMNode',
    'IMInvoke' => 'IMNode',
    'IMInvokeInterface' => 'IMInvoke',
    'IMInvokeSpecial' => 'IMInvoke',
    'IMInvokeStatic' => 'IMInvoke',
    'IMInvokeVirtual' => 'IMInvoke',
    'IMLookupSwitch' => 'IMBranch',
    'IMMonitor' => 'IMNode',
    'IMNew' => 'IMNode',
    'IMNewArray' => 'IMNode',
    'IMNewMultiArray' => 'IMNode',
    'IMNewObjArray' => 'IMNode',
    'IMPopReturnAddr' => 'IMNode',
    'IMPutField' => 'IMNode',
    'IMPutStatic' => 'IMNode',
    'IMReturnSubroutine' => 'IMBranch',
    'IMTableSwitch' => 'IMBranch',
    'IMThrow' => 'IMNode',
    'IMAConstant' => 'IMConstant',
    'IMAMemConstant' => 'IMConstant',
    'IMAMTConstant' => 'IMConstant',
    'IMAReadArray' => 'IMReadArray',
    'IMAReadLocalVariable' => 'IMReadLocalVariable',
    'IMAStoreArray' => 'IMStoreArray',
    'IMAStoreLocalVariable' => 'IMStoreLocalVariable',
    'IMBReadArray' => 'IMReadArray',
    'IMBStoreArray' => 'IMStoreArray',
    'IMCReadArray' => 'IMReadArray',
    'IMCStoreArray' => 'IMStoreArray',
    'IMDAdd' => 'IMAdd',
    'IMDConstant' => 'IMConstant',
    'IMDDiv' => 'IMDiv',
    'IMDMul' => 'IMMul',
    'IMDNeg' => 'IMNeg',
    'IMDReadArray' => 'IMReadArray',
    'IMDReadLocalVariable' => 'IMReadLocalVariable',
    'IMDRem' => 'IMRem',
#    'IMDReturn' => 'IMReturn',
    'IMDStoreArray' => 'IMStoreArray',
    'IMDStoreLocalVariable' => 'IMStoreLocalVariable',
    'IMDSub' => 'IMSub',
    'IMFAdd' => 'IMAdd',
    'IMFConstant' => 'IMConstant',
    'IMFDiv' => 'IMDiv',
    'IMFMul' => 'IMMul',
    'IMFNeg' => 'IMNeg',
    'IMFReadArray' => 'IMReadArray',
    'IMFReadLocalVariable' => 'IMReadLocalVariable',
    'IMFRem' => 'IMRem',
#    'IMFReturn' => 'IMReturn',
    'IMFStoreArray' => 'IMStoreArray',
    'IMFStoreLocalVariable' => 'IMStoreLocalVariable',
    'IMFSub' => 'IMSub',
    'IMIAdd' => 'IMAdd',
    'IMIBitAnd' => 'IMBitAnd',
    'IMIBitOr' => 'IMBitOr',
    'IMIBitXor' => 'IMBitXor',
    'IMIConstant' => 'IMConstant',
    'IMIDiv' => 'IMDiv',
    'IMIMul' => 'IMMul',
    'IMINeg' => 'IMNeg',
    'IMIReadArray' => 'IMReadArray',
    'IMIReadLocalVariable' => 'IMReadLocalVariable',
    'IMIRem' => 'IMRem',
#    'IMIReturn' => 'IMReturn',
#    'IMAReturn' => 'IMReturn',
    'IMIShiftLeft' => 'IMShiftLeft',
    'IMIShiftRight' => 'IMShiftRight',
    'IMIShiftRight2' => 'IMShiftRight2',
    'IMIStoreArray' => 'IMStoreArray',
    'IMIStoreLocalVariable' => 'IMStoreLocalVariable',
    'IMISub' => 'IMSub',
    'IMLAdd' => 'IMAdd',
    'IMLBitAnd' => 'IMBitAnd',
    'IMLBitOr' => 'IMBitOr',
    'IMLBitXor' => 'IMBitXor',
    'IMLConstant' => 'IMConstant',
    'IMLDiv' => 'IMDiv',
    'IMLMul' => 'IMMul',
    'IMLNeg' => 'IMNeg',
    'IMLReadArray' => 'IMReadArray',
    'IMLReadLocalVariable' => 'IMReadLocalVariable',
    'IMLRem' => 'IMRem',
#    'IMLReturn' => 'IMReturn',
    'IMLShiftLeft' => 'IMShiftLeft',
    'IMLShiftRight' => 'IMShiftRight',
    'IMLShiftRight2' => 'IMShiftRight2',
    'IMLStoreArray' => 'IMStoreArray',
    'IMLStoreLocalVariable' => 'IMStoreLocalVariable',
    'IMLSub' => 'IMSub',
    'IMNullConstant' => 'IMConstant',
    'IMSReadArray' => 'IMReadArray',
    'IMSStoreArray' => 'IMStoreArray',
#    'IMVReturn' => 'IMReturn',
); # end %nodes


%trans = (
);

require "templates/typeinfo.pm";
require "templates/init.pm";
require "templates/copyNode.pm";
require "templates/visitNode.pm";
require "templates/costs.pm";
require "templates/processStack.pm";
require "templates/readableString.pm";
require "templates/translate.pm";
require "templates/extras.pm";
require "templates/dumpBC.pm";
require "templates/constant_folding.pm";
require "templates/ssa_optimizer.pm";

if ($ARGV[0] eq 'clear') {
       print "createNodes.pl: Removing automatically generated source files.\n";
       foreach $k (keys %nodes) {
               unlink "builder/keso/compiler/imcode/$k.java";
	       unlink "buildstamps/stamp-imclasses";
       }
       exit 0;
}
print "createNodes.pl: Generating sources of intermediate classes.\n";
open STAMP, ">buildstamps/stamp-imclasses" || die "xxx\n";

foreach $k (keys %nodes) {

	open OUT, ">builder/keso/compiler/imcode/$k.java" || die "xxx\n";
	print STAMP "builder/keso/compiler/imcode/$k.java\n";
	
	print OUT <<ENDOFFILE
/**(c)

  Copyright (C) 2005-2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi\@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.classfile.*;
import keso.classfile.constantpool.*; 
import keso.classfile.datatypes.*; 

import keso.compiler.*;
import keso.compiler.backend.*;
import keso.compiler.kni.*;

import keso.util.Debug; 
import keso.util.DecoratedNames; 
import keso.util.IntegerHashtable; 

import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;

ENDOFFILE
;
	print OUT "\n/* AUTO GENERATED FILE DON'T EDIT */\n\n";

	my $type = $itypes{$k};

	print OUT "public class $k extends $nodes{$k} {\n\n";

	if ($stack{$k.'-vars'}) { print OUT "\t".$stack{$k.'-vars'}."\n"; }

	my $init_method = $init_imnode;
	if ($init{$k}=~/CLASS/) { $init_method = $init{$k}; }
	$init_method="\n\t/* copy constuctor */\n\tprotected \%CLASS\%() { }\n".$init_method;
	if ($init_method=~/domainID/) {
		$init_method=~s/\%DOMID\%/\n\t\tint domainID=0;/gs;
	} else {
		$init_method=~s/\%DOMID\%//gs;
	}
	$init_method=~s/\%CLASS\%/$k/gs;
	$init_method=~s/\%BCTYPE\%/$bctypes{$type}/gs;
	$init_method=~s/\%JTYPE\%/$jtypes{$type}/gs;
	$init_method=~s/\%UJTYPE\%/$jtypes_uppercase{$type}/gs;
	print OUT "$init_method\n";

	if ($copyNode{$k}) {
		my $copyNode_method="\tfinal public IMNode copy(IMCopyVisitor visitor)";
		$copyNode_method.= " throws CompileException {";
		$copyNode_method.= $copyNode{$k}."\n";
		$copyNode_method.="\t\tif (visitor==null) return nnode;\n";
		$copyNode_method.="\t\treturn visitor.visit(nnode,this);\n\t}\n\n";

		$copyNode_method=~s/\%CLASS\%/$k/gs;
		$copyNode_method=~s/\%BCTYPE\%/$bctypes{$type}/gs;
		$copyNode_method=~s/\%JTYPE\%/$jtypes{$type}/gs;
		print OUT "$copyNode_method";
	}

	if ($visitNode{$k}) {
		my $visitNode_method="\tfinal public void visitNodes(IMVisitor visitor)";
		$visitNode_method.=" throws CompileException {";
		$visitNode_method.="\n\t\tif (visitor.skipNode(this)) return;";
		$visitNode_method.= $visitNode{$k}."\n";
		$visitNode_method.="\t}\n\n";

		$visitNode_method=~s/\%CLASS\%/$k/gs;
		$visitNode_method=~s/\%BCTYPE\%/$bctypes{$type}/gs;
		$visitNode_method=~s/\%JTYPE\%/$jtypes{$type}/gs;
		print OUT "$visitNode_method";
	}

	if ($constant_fold{$k}) {
		my $constant_fold_method="\tfinal public IMNode constant_folding()";
		$constant_fold_method.= " throws CompileException {";
		$constant_fold_method.= $constant_fold{$k}."\n";
		$constant_fold_method.=$constant_swap if ($kommutativ{$k} ne ''); 
		$constant_fold_method.="\n\t\treturn this;\n";
		$constant_fold_method.="\t}\n\n";

		$constant_fold_method=~s/\%CLASS\%/$k/gs;
		$constant_fold_method=~s/\%BCTYPE\%/$bctypes{$type}/gs;
		$constant_fold_method=~s/\%JTYPE\%/$jtypes{$type}/gs;
		$constant_fold_method=~s/\%UJTYPE\%/$jtypes_uppercase{$type}/gs;

		$constant_fold_method=~s/\%BIN_OP\%/$bin_op{$k}/gs if $bin_op{$k};

		print OUT "$constant_fold_method";
	}

	if ($ssa_optimize{$k}) {
		my $ssa_opt_method="\tfinal public IMNode ssa_optimize()";
		$ssa_opt_method.=" throws CompileException {";
		#$ssa_opt_method.="\n\t\topts.verbose(\"ssa: \"+toReadableString());\n";
		if ($ssa_optimize{$k}=~/basicBlock/) {
			$ssa_opt_method.="\n\t\tIMBasicBlock basicBlock = method.getCurrentBasicBlock();\n";
			$ssa_opt_method.="\t\tif (basicBlock==null) throw new CompileException(\"no bb in \"+method.getAlias());\n";
		}
		$ssa_opt_method.= $ssa_optimize{$k}."\n";
		$ssa_opt_method.=$constant_swap if ($kommutativ{$k} ne ''); 
		my $n_elem = $ssa_neutral_elem{$k};
		my $c_elem = $ssa_clear_elem{$k};
		if ($n_elem ne '' || $c_elem ne '') {
			$ssa_opt_method.="\n\t\tif (rOpr.isConstant()) {";
			$ssa_opt_method.="\n\t\t\topts.vverbose(\"++ folding x_c x\%BIN_OP\%c \"+toReadableString());";
			$ssa_opt_method.="\n\t\t\t\%JTYPE\% value = rOpr.nodeToConstant().get\%UJTYPE\%Value();";
			$ssa_opt_method.="\n\t\t\tif (value==".$n_elem.") return lOpr;" if $n_elem ne '';
			if ($c_elem ne '') {
				$ssa_opt_method.="\n\t\t\tif (value==".$c_elem.") {";
				$ssa_opt_method.="\n\t\t\t\treturn method.createIM".$itypes{$k};
				$ssa_opt_method.="Constant(".$c_elem.", bcPosition);";
				$ssa_opt_method.="\n\t\t\t}";
			}
			$ssa_opt_method.="\n\t\t}\n";
		}
		$ssa_opt_method.="\n\t\treturn this;\n";
		$ssa_opt_method.="\t}\n\n";

		$ssa_opt_method=~s/\%CLASS\%/$k/gs;
		$ssa_opt_method=~s/\%BCTYPE\%/$bctypes{$type}/gs;
		$ssa_opt_method=~s/\%JTYPE\%/$jtypes{$type}/gs;
		$ssa_opt_method=~s/\%UJTYPE\%/$jtypes_uppercase{$type}/gs;
		$ssa_opt_method=~s/\%CONDITION\%/$ssa_condition{$k}/gs;

		$ssa_opt_method=~s/\%BIN_OP\%/$bin_op{$k}/gs if $bin_op{$k};

		print OUT "$ssa_opt_method";
	}

	if ($extra{$k}) {
		my $ext = $extra{$k};
		$ext=~s/\%CLASS\%/$k/gs;
		print OUT $ext;
	}

	if ($stack{$k}) {
		print OUT "\tpublic IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {";
		print OUT "\n\t\tIMBasicBlock basicBlock = method.getCurrentBasicBlock();\n" if $stack{$k}=~/basicBlock/;
		print OUT $stack{$k};
		print OUT "\t}\n\n";
	}

	print OUT $trans{$k} if $trans{$k};

	if ($readable{$k}) {
		print OUT "\tpublic String toReadableString() {";
		print OUT $readable{$k};
		print OUT "\t}\n\n";
	}

	if ($dumpbc{$k}) {
		print OUT "\tpublic String dumpBC() {";
		print OUT $dumpbc{$k};
		print OUT "}\n\n";
	}

	if ($costs{$k}) {
		my $costs_method="\tpublic int costs() throws CompileException {";
		$costs_method.= $costs{$k};
		$costs_method.="\n\t}\n\n";
		print OUT "$costs_method";
	}

	my $trans_code = $translate{$k};
	if ($trans_code) {
		print OUT "\tpublic void translate(Coder coder) throws CompileException {";
		print OUT "\n\t\tIMBasicBlock basicBlock = method.getCurrentBasicBlock();\n" if $trans_code=~/basicBlock/;
		print OUT "\n\t\tIMMethodFrame frame = method.getMethodFrame();\n" if $trans_code=~/frame/;
		print OUT $trans_code;
		print OUT "\t}\n\n";
	}

	print OUT "}\n";

	close OUT;
}

close STAMP;
