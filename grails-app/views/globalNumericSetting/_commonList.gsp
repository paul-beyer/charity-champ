				<div id="list-globalNumericSetting" class="content scaffold-list" role="main">
					
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<table>
						<thead>
							<tr>
							
								<g:sortableColumn property="name" title="${message(code: 'globalNumericSetting.name.label', default: 'Name')}" />
							
								<g:sortableColumn property="value" title="${message(code: 'globalNumericSetting.value.label', default: 'Value')}" />
							
								<g:sortableColumn property="effectiveDate" title="${message(code: 'globalNumericSetting.effectiveDate.label', default: 'Effective Date')}" />
							
							</tr>
						</thead>
						<tbody>
						<g:each in="${globalNumericSettingInstanceList}" status="i" var="globalNumericSettingInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
								<td><g:link action="show" id="${globalNumericSettingInstance.id}">${fieldValue(bean: globalNumericSettingInstance, field: "name")}</g:link></td>
							
								<td>${fieldValue(bean: globalNumericSettingInstance, field: "value")}</td>
							
								<td><g:formatDate date="${globalNumericSettingInstance.effectiveDate}" /></td>
							
							</tr>
						</g:each>
						</tbody>
					</table>
					<div class="pagination">
						<g:paginate total="${globalNumericSettingInstanceTotal}" />
					</div>
				</div>
			