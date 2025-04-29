select proj.name proj_name,
       stype.type segment_type,
	   lang.language language,
       tuv.createdate,
	   tuv.usecount,
	   tuv.usedate,
	   tuv.reviewed,
	   tuv.segment
  from translationproject proj, 
       translationunit tu, 
	   translationunitvariant tuv,
	   language lang,
	   segmenttype stype
  where tuv.tuid = tu.id and 
        tu.projid = proj.id and
		tu.segmenttype = stype.id and
		tuv.language = lang.id;